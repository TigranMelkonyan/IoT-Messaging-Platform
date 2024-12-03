package com.tigran.oauth.model.rest.request.account;

import com.tigran.oauth.model.validation.ValidatableRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 5:31 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountNotificationInfoModel extends ValidatableRequest {

    private boolean enableNotifications;
    private boolean emailNotificationEnabled;
    private boolean smsNotificationEnabled;
    private boolean pushNotificationEnabled;
}
