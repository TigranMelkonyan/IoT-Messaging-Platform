package com.tigran.oauth.service.user.impl;

import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.rest.request.user.CreateUserRequest;
import com.tigran.oauth.domain.model.rest.request.user.UpdateUserRequest;
import com.tigran.oauth.repository.user.UserRepository;
import com.tigran.oauth.service.user.UserService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:00 PM
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User createOrGet(final CreateUserRequest request) {
        log.info("Creating user by request - {} ", request);
        Assert.notNull(request, "Request must not be null");
        Optional<User> user = repository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            return user.get();
        }
        User entity = request.toEntity();
        User result = repository.save(entity);
        log.info("Successfully created user by request - {}, result - {}", request, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(final UUID id) {
        return null;
    }

    @Override
    @Transactional
    public User update(final UUID id, final UpdateUserRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void delete(final UUID id, final boolean deleteFromDb) {

    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<User> search(final SearchProperties searchProperties, final AccountOrderByOption orderByOption) {
        return null;
    }

}
