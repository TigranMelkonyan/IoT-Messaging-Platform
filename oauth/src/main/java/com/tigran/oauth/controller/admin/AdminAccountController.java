package com.tigran.oauth.controller.admin;

import com.tigran.oauth.controller.AbstractController;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.model.rest.response.account.AccountResponse;
import com.tigran.oauth.domain.model.rest.response.page.PageResponse;
import com.tigran.oauth.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 12:18 PM
 */
@RestController
@RequestMapping("api/admin/accounts")
@Tag(name = "Admin account API")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class AdminAccountController extends AbstractController {

    private final AccountService accountService;

    public AdminAccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Account")
    public ResponseEntity<AccountResponse> get(@PathVariable("id") final UUID id) {
        Account account = accountService.getById(id);
        return respondOK(AccountResponse.from(account));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Account")
    public ResponseEntity<?> delete(
            @PathVariable("id") final UUID id,
            @RequestParam final boolean deleteFromDb) {
        accountService.delete(id, deleteFromDb);
        return respondEmpty();
    }

    @PutMapping("{id}")
    @Operation(summary = "Inactivate Account")
    public ResponseEntity<?> update(@PathVariable("id") final UUID id) {
        accountService.inactivateAccount(id);
        return respondEmpty();
    }

    @GetMapping("search/{page}/{size}")
    @Operation(summary = "Search Accounts with pages")
    public ResponseEntity<PageResponse<AccountResponse>> getPagesForDevices(
            @Valid final SearchProperties searchProperties,
            @PathVariable final int page,
            @PathVariable final int size,
            @RequestParam final AccountOrderByOption orderBy) {
        PageModel<Account> result = accountService.search(searchProperties, page, size, orderBy);
        PageResponse<AccountResponse> response = new PageResponse<>(result
                .getItems()
                .stream().map(AccountResponse::from)
                .collect(Collectors.toList()),
                result.getTotalCount());
        return respondOK(response);
    }

}
