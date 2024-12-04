package com.tigran.oauth.domain.model.rest.request.oauth;

import com.tigran.oauth.domain.model.validation.ValidatableRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 11:38 AM
 */
@Getter
@Setter
public class OauthTokenRequest extends ValidatableRequest {

    @NotBlank(message = "required")
    private String userName;

    @NotBlank(message = "required")
    private String password;
}
