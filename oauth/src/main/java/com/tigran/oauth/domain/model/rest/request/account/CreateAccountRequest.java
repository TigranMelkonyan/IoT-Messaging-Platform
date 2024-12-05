package com.tigran.oauth.domain.model.rest.request.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tigran.oauth.domain.model.common.role.Role;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.rest.request.user.CreateUserRequest;
import com.tigran.oauth.domain.model.validation.ValidatableRequest;
import com.tigran.oauth.util.password.PasswordUtils;
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

    @JsonIgnore
    private Role role;

    @JsonIgnore
    private String firebaseClientToken;
    
    @JsonIgnore
    private boolean verified;

    public Account toEntity(final User user) {
        Account account = new Account();
        account.setUserName(this.userName);
        account.setRole(this.role);
        account.setUser(user);
        account.setPassword(PasswordUtils.encode(this.password));
        account.setFirebaseClientToken(this.firebaseClientToken);
        account.setVerified(this.verified);
        return account;
    }

}
