package com.tigran.oauth.repository.user;

import com.tigran.oauth.model.common.PageModel;
import com.tigran.oauth.model.common.SearchProperties;
import com.tigran.oauth.model.common.UserOrderByOption;
import com.tigran.oauth.model.entity.user.User;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:07 PM
 */
public interface UserRepositoryCustom {

    PageModel<User> search(final SearchProperties searchProperties, final UserOrderByOption orderByOption);
}
