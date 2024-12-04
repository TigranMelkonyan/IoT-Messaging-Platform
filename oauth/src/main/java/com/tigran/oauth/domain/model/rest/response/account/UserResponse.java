package com.tigran.oauth.domain.model.rest.response.account;

import com.tigran.oauth.domain.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 2:30 PM
 */
@Getter
@Setter
@ToString
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    public static UserResponse from(final User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        return userResponse;
    }
}
