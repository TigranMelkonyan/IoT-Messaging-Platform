package com.tigran.api.application.usecase;

import com.tigran.api.adapter.outbound.notification.NotificationCenterService;
import com.tigran.api.adapter.outbound.websocket.WebSocketPublisherService;
import com.tigran.api.application.usecase.notificationinfo.NotificationReceiverInfoService;
import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.model.entity.device.data.DeviceData;
import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.model.notification.reciver.NotificationType;
import com.tigran.api.domain.model.rabbitmq.DevicePublishData;
import com.tigran.api.domain.port.inbound.device.DeviceService;
import com.tigran.api.domain.port.inbound.device.data.DeviceDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final NotificationCenterService notificationCenterService;
    private final WebSocketPublisherService webSocketPublisher;
    private final NotificationReceiverInfoService notificationReceiverInfoService;

    public DeviceDataProcessingService(
            final DeviceService deviceService,
            final DeviceDataService deviceDataService,
            final NotificationCenterService notificationCenterService,
            final WebSocketPublisherService webSocketPublisher,
            final NotificationReceiverInfoService notificationReceiverInfoService) {
        this.deviceService = deviceService;
        this.deviceDataService = deviceDataService;
        this.notificationCenterService = notificationCenterService;
        this.webSocketPublisher = webSocketPublisher;
        this.notificationReceiverInfoService = notificationReceiverInfoService;
    }

    public void processDeviceData(final DevicePublishData data) {
        try {
            //Preserving data
            Device device = deviceService.getByMacAddress(data.getMacAddress());
            log.info("Processing device data for device with macAddress - {}", data.getMacAddress());

            //Sending data to socket
            webSocketPublisher.publishData(data);
            DeviceData deviceData = mapToDeviceData(data, device);
            deviceDataService.save(deviceData);

            //Sending notifications
            List<NotificationReceiverInfo> receivers = notificationReceiverInfoService.getNotificationReceivers();
            notifyReceivers(receivers);
        } catch (Exception e) {
            log.warn("Error message processing device data - {}", e.getMessage());
        }
        log.info("Successfully processed device data for device macAddress - {}", data.getMacAddress());
    }

    private void notifyReceivers(final List<NotificationReceiverInfo> receivers) {
        for (NotificationReceiverInfo receiver : receivers) {
            if (receiver.isEmailNotificationEnabled()) {
                notificationCenterService.sendByType(
                        NotificationReceiverInfo.builder().email(receiver.getEmail()).build(),
                        buildMessage(),
                        NotificationType.EMAIL);
            }
            if (receiver.isSmsNotificationEnabled()) {
                notificationCenterService.sendByType(
                        NotificationReceiverInfo.builder().email(receiver.getPhone()).build(),
                        buildMessage(),
                        NotificationType.SMS);
            }
            if (receiver.isEmailNotificationEnabled()) {
                notificationCenterService.sendByType(
                        NotificationReceiverInfo.builder().email(receiver.getFirebaseClientToken()).build(),
                        buildMessage(),
                        NotificationType.PUSH);
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
