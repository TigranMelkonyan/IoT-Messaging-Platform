package com.tigran.api.domain.port.outbound.device;

import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 5:46 PM
 */
public interface NotificationSender {

    void send(final NotificationReceiverInfo receiverInfo,
              final MessageContent messageContent);
}
