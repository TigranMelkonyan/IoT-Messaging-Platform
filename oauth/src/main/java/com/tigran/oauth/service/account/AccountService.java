package com.tigran.oauth.service.account;

import com.tigran.oauth.model.common.PageModel;
import com.tigran.oauth.model.common.SearchProperties;
import com.tigran.oauth.model.entity.account.Account;
import com.tigran.oauth.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.model.rest.request.account.UpdateAccountRequest;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 6:08 PM
 */
public interface AccountService {

    Account createOrGet(final CreateAccountRequest request);

    Account getById(final UUID id);

    Account update(final UUID id, final UpdateAccountRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    PageModel<Account> search(final SearchProperties searchProperties);
}
