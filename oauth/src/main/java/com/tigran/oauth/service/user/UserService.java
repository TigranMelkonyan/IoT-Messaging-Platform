package com.tigran.oauth.service.user;

import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.rest.request.user.CreateUserRequest;
import com.tigran.oauth.domain.model.rest.request.user.UpdateUserRequest;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 2:59 PM
 */
public interface UserService {

    User createOrGet(final CreateUserRequest request);

    User getById(final UUID id);

    User update(final UUID id, final UpdateUserRequest request);

    void delete(final UUID id, final boolean deleteFromDb);

    PageModel<User> search(final SearchProperties searchProperties, final AccountOrderByOption orderByOption);
}
