package com.tigran.oauth.controller.account;

import com.tigran.oauth.controller.AbstractController;
import com.tigran.oauth.domain.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.service.account.mediator.AccountMediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 12:18 PM
 */
@RestController
@RequestMapping("api/account-registration")
@Tag(name = "Register account API")
public class AccountRegistrationController extends AbstractController {

    private final AccountMediator accountMediator;

    public AccountRegistrationController(final AccountMediator accountMediator) {
        this.accountMediator = accountMediator;
    }

    @PutMapping
    @Operation(summary = "Register new Account")
    public ResponseEntity<?> register(
            @RequestParam final String verificationTokenId,
            @Valid @RequestBody CreateAccountRequest request) {
        accountMediator.registerAccount(verificationTokenId, request);
        return respondEmpty();
    }

}
