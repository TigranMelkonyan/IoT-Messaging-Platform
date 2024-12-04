package com.tigran.oauth.controller.oauth;

import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.model.exception.ErrorCode;
import com.tigran.oauth.domain.model.exception.RecordConflictException;
import com.tigran.oauth.domain.model.rest.request.oauth.OauthTokenRequest;
import com.tigran.oauth.domain.model.rest.response.oauth.OauthTokenResponse;
import com.tigran.oauth.service.account.AccountService;
import com.tigran.oauth.service.security.JwtService;
import com.tigran.oauth.util.password.PasswordUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 11:35 AM
 */
@RestController
@RequestMapping("api/oauth")
public class OauthTokenController {

    private final AccountService accountService;
    private final JwtService jwtService;

    public OauthTokenController(final AccountService accountService, final JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public OauthTokenResponse getToken(
            @RequestBody OauthTokenRequest request
    ) {
        Account account = accountService.getByUserName(request.getUserName());
        if (!PasswordUtils.isPasswordMatch(request.getPassword(), account.getPassword())) {
            throw new RecordConflictException("Invalid credentials", ErrorCode.INVALID_CREDENTIALS);
        }
        String token = jwtService.createJwt(account);
        return new OauthTokenResponse(token);
    }
}
