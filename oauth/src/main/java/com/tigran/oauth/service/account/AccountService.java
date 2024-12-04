package com.tigran.oauth.service.account;

import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.domain.model.rest.request.account.UpdateAccountRequest;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 6:08 PM
 */
public interface AccountService {

    Account create(final CreateAccountRequest request);

    Account getById(final UUID id);

    Account getByUserName(final String userName);

    Account update(final UUID id, final UpdateAccountRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    void inactivateAccount(final UUID id);

    PageModel<Account> search(final SearchProperties searchProperties,
                              final int page,
                              final int size,
                              final AccountOrderByOption orderBy);
}
