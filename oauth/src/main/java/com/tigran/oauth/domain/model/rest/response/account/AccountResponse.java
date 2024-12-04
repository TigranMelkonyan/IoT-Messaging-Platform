package com.tigran.oauth.domain.model.rest.response.account;

import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.entity.account.AccountNotificationInfo;
import com.tigran.oauth.domain.model.common.role.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 2:29 PM
 */
@Getter
@Setter
@ToString
public class AccountResponse {

    private UserResponse userResponse;
    private AccountNotificationInfo notificationInfo;
    private String userName;
    private Role role;
    private boolean verified = false;
    private boolean active = true;

    public static AccountResponse from(final Account account) {
        AccountResponse response = new AccountResponse();
        response.setUserResponse(UserResponse.from(account.getUser()));
        response.setNotificationInfo(account.getNotificationInfo());
        response.setUserName(account.getUserName());
        response.setRole(account.getRole());
        response.setVerified(account.isVerified());
        response.setActive(account.isActive());
        return response;
    }
}
