package com.tigran.api.adapter.outbound.notification;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.tigran.api.domain.model.notification.reciver.FirebaseUserInfo;
import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.port.outbound.device.NotificationSender;
import com.tigran.api.domain.port.outbound.firebase.FirebaseService;
import com.tigran.api.infrastructure.util.password.PasswordGeneratorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 6:23 PM
 */
@Component
@Log4j2
public class PushSenderService implements NotificationSender, FirebaseService {

    private final FirebaseApp defaultApp;

    public PushSenderService(final FirebaseApp defaultApp) {
        this.defaultApp = defaultApp;
    }

    @Override
    public void send(final NotificationReceiverInfo receiverInfo, final MessageContent messageContent) {
        sendPushNotification(
                receiverInfo.getFirebaseClientToken(), messageContent.getTitle(),
                messageContent.getText(), messageContent.getTopic());
    }

    @Override
    public FirebaseUserInfo createOrGetUser(final NotificationReceiverInfo receiverInfo) {
        UserRecord userRecord;
        try {
            Optional<UserRecord> record = getUserByEmail(receiverInfo.getEmail());
            if (record.isPresent()) {
                return FirebaseUserInfo.from(record.get());
            }
            verifyToken(receiverInfo.getFirebaseClientToken());
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(receiverInfo.getEmail())
                    .setEmailVerified(true)
                    .setPassword(PasswordGeneratorUtils.generateRandomPassword(12))
                    .setPhoneNumber(receiverInfo.getPhone())
                    .setDisplayName(receiverInfo.getUserName())
                    .setDisabled(false);
            userRecord = FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            log.info("Cannot create user, reason - {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Successfully created user, id - {}", userRecord.getUid());
        return FirebaseUserInfo.from(userRecord);
    }

    @Override
    public FirebaseUserInfo updateUser(final String uid, final NotificationReceiverInfo receiverInfo) {
        UserRecord userRecord;
        try {
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                    .setEmail(receiverInfo.getEmail())
                    .setEmailVerified(true)
                    .setPassword(PasswordGeneratorUtils.generateRandomPassword(12))
                    .setPhoneNumber(receiverInfo.getPhone())
                    .setDisplayName(receiverInfo.getUserName())
                    .setDisabled(false);
            userRecord = FirebaseAuth.getInstance().updateUser(request);
        } catch (FirebaseAuthException e) {
            log.info("Cannot update user, reason - {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Successfully create user, id - {}", userRecord.getUid());
        return FirebaseUserInfo.from(userRecord);
    }

    @Override
    public void deleteUser(final String uid) {
        try {
            UserRecord user = FirebaseAuth.getInstance().getUser(uid);
            if (Objects.nonNull(user)) {
                FirebaseAuth.getInstance().deleteUser(uid);
            }
        } catch (FirebaseAuthException e) {
            log.info("Cannot delete user, reason - {}", e.getMessage());
        }
        log.info("Successfully deleted user, id - {}", uid);
    }

    @Override
    public void subscribeToTopic(final String token, final String topic) {
        verifyToken(toString());
        try {
            FirebaseMessaging.getInstance()
                    .subscribeToTopic(Collections.singletonList(token), topic);
        } catch (FirebaseMessagingException e) {
            log.info("Cannot subscribed user to topic, reason - {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Successfully subscribed to topic - {}", topic);
    }

    @Override
    public void unsubscribeFromTopic(final String token, final String topic) {
        verifyToken(toString());
        try {
            FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(token), topic);
        } catch (FirebaseMessagingException e) {
            log.info("Cannot unsubscribed user to topic, reason - {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Successfully unsubscribed to topic - {}", topic);
    }

    @Override
    public void sendPushNotification(final String userToken, final String title, final String text, final String topic) {
        verifyToken(toString());
        Message message = Message.builder()
                .setTopic(topic)
                .setToken(userToken)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(text)
                        .build())
                .build();
        try {
            FirebaseMessaging.getInstance(defaultApp).sendAsync(message).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Cannot send push notification, reason - {}", e.getMessage());
        }
    }

    @Override
    public List<FirebaseUserInfo> getAllUsers() {
        List<FirebaseUserInfo> response = new ArrayList<>();
        try {
            ListUsersPage usersPage = FirebaseAuth.getInstance().listUsers(null);
            usersPage.streamAll().forEach(u -> response.add(new FirebaseUserInfo(
                    u.getUid(), u.getDisplayName(),
                    u.getEmail(), u.getPhoneNumber())));
        } catch (FirebaseAuthException e) {
            log.error("Cannot get users, reason - {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
        return response;
    }

    private Optional<UserRecord> getUserByEmail(final String email) {
        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            log.info("Cannot get user, reason - {}", e.getMessage());
        }
        return Optional.ofNullable(userRecord);
    }

    private void verifyToken(final String idToken) {
        try {
            FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            log.info("Failed to verify token, reason - {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
