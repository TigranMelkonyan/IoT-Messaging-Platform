package com.tigran.api.application.usecase.device.data;

import com.tigran.api.adapter.outbound.notification.NotificationCenterService;
import com.tigran.api.adapter.outbound.notification.template.NotificationTemplateServiceImpl;
import com.tigran.api.adapter.outbound.websocket.WebSocketPublisherService;
import com.tigran.api.application.usecase.notificationinfo.NotificationReceiverInfoService;
import com.tigran.api.application.usecase.threshold.TimeRangeValidatorImpl;
import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.model.notification.reciver.NotificationType;
import com.tigran.api.domain.model.rabbitmq.DevicePublishData;
import com.tigran.api.domain.port.inbound.device.DeviceService;
import com.tigran.api.domain.port.inbound.device.data.DeviceDataService;
import com.tigran.api.domain.port.inbound.threshold.ThresholdEvaluator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 5:37 PM
 */
@Service
@Log4j2
public class DeviceDataProcessingService {

    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;
    private final TimeRangeValidatorImpl timeRangeValidator;
    private final WebSocketPublisherService webSocketPublisher;
    private final ThresholdEvaluator thresholdEvaluatorService;
    private final NotificationCenterService notificationCenterService;
    private final NotificationTemplateServiceImpl notificationTemplateService;
    private final NotificationReceiverInfoService notificationReceiverInfoService;

    public DeviceDataProcessingService(
            final DeviceService deviceService,
            final DeviceDataService deviceDataService,
            final TimeRangeValidatorImpl timeRangeValidator,
            final WebSocketPublisherService webSocketPublisher,
            final ThresholdEvaluator thresholdEvaluatorService,
            final NotificationCenterService notificationCenterService,
            final NotificationTemplateServiceImpl notificationTemplateService,
            final NotificationReceiverInfoService notificationReceiverInfoService) {
        this.deviceService = deviceService;
        this.deviceDataService = deviceDataService;
        this.timeRangeValidator = timeRangeValidator;
        this.webSocketPublisher = webSocketPublisher;
        this.thresholdEvaluatorService = thresholdEvaluatorService;
        this.notificationCenterService = notificationCenterService;
        this.notificationTemplateService = notificationTemplateService;
        this.notificationReceiverInfoService = notificationReceiverInfoService;
    }

    public void processDeviceData(final DevicePublishData data) {
        try {
            //Preserving data
            log.info("Processing device data for device with macAddress - {}", data.getMacAddress());
            Device device = deviceService.getByMacAddress(data.getMacAddress());

            //Sending data to socket
            webSocketPublisher.publishData(data);
            DeviceData deviceData = mapToDeviceData(data, device);
            deviceDataService.save(deviceData);

            //Sending notifications
            NotificationTemplate template = notificationTemplateService.getByDeviceId(device.getId());

            if (template.isEnableNotifications()) {
                if (thresholdEvaluatorService.evaluateThreshold(deviceData, template)) {
                    LocalTime sendStartTime = template.getSendStartTime();
                    LocalTime sendEndTime = template.getSendEndTime();
                    List<NotificationReceiverInfo> receivers = notificationReceiverInfoService.getNotificationReceivers();

                    //Checking time sensitive info
                    if (Objects.isNull(sendStartTime) || Objects.isNull(sendEndTime)) {
                        notifyReceivers(receivers);
                    } else if (timeRangeValidator.checkIsWithinTimeRange(sendStartTime, sendEndTime, template.getZoneId())) {
                        notifyReceivers(receivers);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Error message processing device data - {}", e.getMessage());
        }
        log.info("Successfully processed device data for device macAddress - {}", data.getMacAddress());
    }

    private void notifyReceivers(final List<NotificationReceiverInfo> receivers) {
        for (NotificationReceiverInfo receiver : receivers) {
            if (receiver.isEmailNotificationEnabled()) {
                notificationCenterService.sendByType(NotificationReceiverInfo
                        .builder()
                        .email(receiver.getEmail())
                        .build(), buildMessage(), NotificationType.EMAIL);
            }
            if (receiver.isSmsNotificationEnabled()) {
                notificationCenterService.sendByType(NotificationReceiverInfo
                        .builder()
                        .email(receiver.getPhone())
                        .build(), buildMessage(), NotificationType.SMS);
            }
            if (receiver.isEmailNotificationEnabled()) {
                notificationCenterService.sendByType(NotificationReceiverInfo
                        .builder()
                        .email(receiver.getFirebaseClientToken())
                        .build(), buildMessage(), NotificationType.PUSH);
            }
        }
    }

    private MessageContent buildMessage() {
        return MessageContent
                .builder()
                .title("Customize title")
                .text("Customize text")
                .build();
    }

    private DeviceData mapToDeviceData(final DevicePublishData data, final Device device) {
        DeviceData deviceData = new DeviceData();
        deviceData.setDevice(device);
        deviceData.setHumidity(data.getHumidity());
        deviceData.setTemperature(data.getTemperature());
        deviceData.setBatteryLevel(data.getBatteryLevel());
        deviceData.setTimestamp(data.getTimestamp());
        return deviceData;
    }
}
