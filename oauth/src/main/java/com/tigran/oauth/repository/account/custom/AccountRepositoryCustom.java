package com.tigran.oauth.repository.account.custom;

import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.entity.account.Account;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 10:33 AM
 */
public interface AccountRepositoryCustom {

    PageModel<Account> searchAccounts(
            final SearchProperties searchProperties,
            final int page,
            final int size,
            final AccountOrderByOption orderBy);
}
