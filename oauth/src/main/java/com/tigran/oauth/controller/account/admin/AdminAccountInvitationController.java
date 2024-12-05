package com.tigran.oauth.controller.account.admin;

import com.tigran.oauth.controller.AbstractController;
import com.tigran.oauth.domain.model.common.role.Role;
import com.tigran.oauth.service.account.mediator.AccountMediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 12:18 PM
 */
@RestController
@RequestMapping("api/admin/account-invitation")
@Tag(name = "Admin account invitation API")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class AdminAccountInvitationController extends AbstractController {

    private final AccountMediator accountMediator;

    public AdminAccountInvitationController(final AccountMediator accountMediator) {
        this.accountMediator = accountMediator;
    }

    @PutMapping
    @Operation(summary = "Send Account registration invitation")
    public ResponseEntity<?> invite(@RequestParam final String email, @RequestParam final Role role) {
        accountMediator.sendAccountRegistrationInvitation(email, role);
        return respondEmpty();
    }

}
