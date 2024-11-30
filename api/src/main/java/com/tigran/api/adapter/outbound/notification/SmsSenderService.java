package com.tigran.api.adapter.outbound.notification;

import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.port.outbound.device.NotificationSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 6:23 PM
 */
@Component
public class SmsSenderService implements NotificationSender {

    private final String senderPhoneNumber;

    public SmsSenderService(final @Value("${twilio.phone.number}") String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    @Override
    public void send(final NotificationReceiverInfo receiverInfo, final MessageContent messageContent) {
        sendSmsNotification(receiverInfo.getPhone(), messageContent.getTitle(), messageContent.getText());
    }

    private void sendSmsNotification(final String phone, final String title, final String text) {
        String message = title + "\n" + text;
        Message.creator(
                        new PhoneNumber(phone),
                        new PhoneNumber(senderPhoneNumber),
                        message)
                .create();
    }
}
