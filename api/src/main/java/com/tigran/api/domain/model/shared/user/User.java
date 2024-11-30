package com.tigran.api.domain.model.shared.user;

import lombok.Data;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 12:57 PM
 */
@Data
public class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String firebaseClientToken;
    private boolean enableNotifications;
    private UserNotificationInfo notificationInfo;
}
