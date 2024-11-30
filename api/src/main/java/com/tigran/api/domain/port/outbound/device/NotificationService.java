package com.tigran.api.domain.port.outbound.device;

import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.model.notification.reciver.NotificationType;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 12:21 PM
 */
public interface NotificationService {

    void sendByType(
            final NotificationReceiverInfo receiverInfo,
            final MessageContent messageContent,
            final NotificationType type);
}
