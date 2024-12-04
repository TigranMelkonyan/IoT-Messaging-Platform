package com.tigran.api.domain.model.shared.user;

import lombok.Data;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 1:07 PM
 */
@Data
public class UserAccountNotificationInfo {

    private boolean emailNotificationEnabled;
    private boolean smsNotificationEnabled;
    private boolean pushNotificationEnabled;
}
