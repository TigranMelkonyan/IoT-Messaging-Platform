package com.tigran.oauth.repository.user;

import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.entity.user.User;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:07 PM
 */
public interface UserRepositoryCustom {

    PageModel<User> search(final SearchProperties searchProperties, final AccountOrderByOption orderByOption);
}
