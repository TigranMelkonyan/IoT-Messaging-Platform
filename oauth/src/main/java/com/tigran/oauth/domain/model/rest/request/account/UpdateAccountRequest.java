package com.tigran.oauth.domain.model.rest.request.account;

import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.rest.request.user.UpdateUserRequest;
import com.tigran.oauth.domain.model.validation.ValidatableRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 5:15 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateAccountRequest extends ValidatableRequest {

    @NotNull(message = "required")
    private UpdateUserRequest user;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private UpdateAccountNotificationInfo notificationInfo;

    public Account toEntity(final Account account, final User user) {
        account.setUserName(this.userName);
        account.setPassword(this.password);
        account.setUser(user);
        account.setNotificationInfo(notificationInfo.from(account.getNotificationInfo()));
        return account;
    }

}
