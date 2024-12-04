package com.tigran.api.domain.model.notification.reciver;

import com.tigran.api.domain.model.shared.user.UserAccount;
import com.tigran.api.domain.model.shared.user.UserAccountNotificationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 6:05 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationReceiverInfo {

    private String userName;
    private String email;
    private String phone;
    private String firebaseClientToken;
    private boolean emailNotificationEnabled;
    private boolean smsNotificationEnabled;
    private boolean pushNotificationEnabled;

    public NotificationReceiverInfo from(final UserAccount user) {
        NotificationReceiverInfo receiverInfo = new NotificationReceiverInfo();
        receiverInfo.setEmail(user.getEmail());
        receiverInfo.setPhone(user.getPhone());
        receiverInfo.setUserName(user.getPhone());
        UserAccountNotificationInfo info = user.getNotificationInfo();
        receiverInfo.setEmailNotificationEnabled(info.isEmailNotificationEnabled());
        receiverInfo.setSmsNotificationEnabled(info.isSmsNotificationEnabled());
        receiverInfo.setPushNotificationEnabled(info.isPushNotificationEnabled());
        return receiverInfo;
    }
}
