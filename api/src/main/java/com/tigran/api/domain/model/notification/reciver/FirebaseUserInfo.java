package com.tigran.api.domain.model.notification.reciver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.auth.UserRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 8:12 PM
 */
@Getter
@Setter
@NoArgsConstructor
public class FirebaseUserInfo {

    private String userId;
    private String userName;
    private String email;
    private String phone;

    @JsonIgnore
    private String firebasePassword;

    public FirebaseUserInfo(
            final String userId,
            final String userName,
            final String email,
            final String phone) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public static FirebaseUserInfo from(final UserRecord userRecord) {
        return new FirebaseUserInfo(
                userRecord.getUid(), userRecord.getDisplayName(),
                userRecord.getEmail(), userRecord.getPhoneNumber()
        );
    }
}
