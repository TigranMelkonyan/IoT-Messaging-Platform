package com.tigran.oauth.domain.model.rest.request.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tigran.oauth.domain.model.common.role.Role;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.rest.request.user.CreateUserRequest;
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
public class CreateAccountRequest extends ValidatableRequest {

    @NotNull(message = "required")
    private CreateUserRequest user;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String password;

    @NotNull
    @JsonIgnore
    private Role role;

    @JsonIgnore
    private String firebaseClientToken;

    public Account toEntity(final User user) {
        Account account = new Account();
        account.setUserName(this.userName);
        account.setRole(this.role);
        account.setUser(user);
        account.setPassword(this.password);
        account.setFirebaseClientToken(this.firebaseClientToken);
        return account;
    }

}
