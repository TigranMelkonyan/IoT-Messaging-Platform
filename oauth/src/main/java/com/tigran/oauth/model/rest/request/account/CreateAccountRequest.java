package com.tigran.oauth.model.rest.request.account;

import com.tigran.oauth.model.entity.account.Account;
import com.tigran.oauth.model.rest.request.user.CreateUserRequest;
import com.tigran.oauth.model.validation.ValidatableRequest;
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
    
    public Account toEntity() {
        Account account = new Account();
        account.setUserName(this.userName);
        account.setUser(this.user.toEntity());
        return account;
    }

}
