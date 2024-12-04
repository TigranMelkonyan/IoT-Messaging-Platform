package com.tigran.oauth.domain.model.rest.request.user;

import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.validation.ValidatableRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 1:51 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateUserRequest extends ValidatableRequest {
    
    @NotBlank(message = "required")
    private String firstName;

    @NotBlank(message = "required")
    private String lastName;

    @NotBlank(message = "required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email address")
    private String email;

    @NotBlank(message = "required")
    private String phone;

    public User toEntity(final User entity) {
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setEmail(this.email);
        entity.setPhone(this.phone);
        return entity;
    }
}
