package com.tigran.api.application.usecase.notificationinfo;

import com.tigran.api.adapter.outbound.feign.UserResourceClient;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.model.shared.user.UserAccount;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 2:03 PM
 */
@Component
@Log4j2
public class NotificationReceiverInfoService {

    private final UserResourceClient userResourceClient;

    public NotificationReceiverInfoService(final UserResourceClient userResourceClient) {
        this.userResourceClient = userResourceClient;
    }

    public List<NotificationReceiverInfo> getNotificationReceivers() {
        List<UserAccount> list = userResourceClient.getAllUsersWithEnabledNotification();
        List<NotificationReceiverInfo> receiverInfos = new ArrayList<>();
        list.forEach(user -> receiverInfos.add(new NotificationReceiverInfo()));
        return receiverInfos;
    }
}
