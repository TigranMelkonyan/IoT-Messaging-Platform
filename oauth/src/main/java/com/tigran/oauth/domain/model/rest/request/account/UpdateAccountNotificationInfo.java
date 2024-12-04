package com.tigran.oauth.domain.model.rest.request.account;

import com.tigran.oauth.domain.entity.account.AccountNotificationInfo;
import com.tigran.oauth.domain.model.validation.ValidatableRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 5:31 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateAccountNotificationInfo extends ValidatableRequest {

    private boolean emailNotificationEnabled;
    private boolean smsNotificationEnabled;
    private boolean pushNotificationEnabled;

    public AccountNotificationInfo from(final AccountNotificationInfo notificationInfo) {
        notificationInfo.setEmailNotificationEnabled(emailNotificationEnabled);
        notificationInfo.setSmsNotificationEnabled(smsNotificationEnabled);
        notificationInfo.setPushNotificationEnabled(pushNotificationEnabled);
        return notificationInfo;
    }
}
