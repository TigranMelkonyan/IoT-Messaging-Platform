package com.tigran.api.adapter.outbound.notification;

import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.model.notification.reciver.NotificationType;
import com.tigran.api.domain.port.outbound.device.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 5:54 PM
 */
@Service
@Log4j2
public class NotificationCenterService implements NotificationService {

    private final EmailSenderService emailSenderService;
    private final SmsSenderService smsSenderService;
    private final PushSenderService pushSenderService;

    public NotificationCenterService(
            final EmailSenderService emailSenderService,
            final SmsSenderService smsSenderService,
            final PushSenderService pushSenderService) {
        this.emailSenderService = emailSenderService;
        this.smsSenderService = smsSenderService;
        this.pushSenderService = pushSenderService;
    }

    @Override
    public void sendByType(
            final NotificationReceiverInfo receiverInfo,
            final MessageContent messageContent,
            final NotificationType type) {
        switch (type) {
            case EMAIL:
                emailSenderService.send(receiverInfo, messageContent);
                log.info("Successfully sent notification by email type to user with email - {}", receiverInfo.getEmail());
                break;
            case SMS:
                smsSenderService.send(receiverInfo, messageContent);
                log.info("Successfully sent notification by sms to user with phone - {}", receiverInfo.getPhone());
                break;
            case PUSH:
                pushSenderService.send(receiverInfo, messageContent);
                log.info("Successfully sent notification by push type to user with id - {}", receiverInfo.getFirebaseClientToken());
                break;
            default:
                log.error("Unknown notification type: {}", type);
                throw new IllegalArgumentException("Unknown notification type");
        }
    }
}
