package com.tigran.api.domain.port.outbound.firebase;

import com.tigran.api.domain.model.notification.reciver.FirebaseUserInfo;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;

import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 8:11 PM
 */
public interface FirebaseService {

    FirebaseUserInfo createOrGetUser(final NotificationReceiverInfo receiverInfo);

    FirebaseUserInfo updateUser(final String uid, final NotificationReceiverInfo receiverInfo);

    void deleteUser(final String uid);

    void subscribeToTopic(final String token, final String topic);

    void sendPushNotification(final String userToken, final String title, final String text, final String topic);

    void unsubscribeFromTopic(final String token, final String topic);

    List<FirebaseUserInfo> getAllUsers();
}
