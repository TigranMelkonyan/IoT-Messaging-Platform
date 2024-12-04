package com.tigran.oauth.service.account.impl;

import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.domain.entity.common.base.ModelStatus;
import com.tigran.oauth.domain.entity.user.User;
import com.tigran.oauth.domain.model.exception.ErrorCode;
import com.tigran.oauth.domain.model.exception.RecordConflictException;
import com.tigran.oauth.domain.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.domain.model.rest.request.account.UpdateAccountRequest;
import com.tigran.oauth.repository.account.AccountRepository;
import com.tigran.oauth.service.account.AccountService;
import com.tigran.oauth.service.user.UserService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 6:11 PM
 */
@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final UserService userService;

    public AccountServiceImpl(final AccountRepository repository, final UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Account create(final CreateAccountRequest request) {
        log.info("Creating Account by request - {} ", request);
        Assert.notNull(request, "Request must not be null");
        assertNotExists(request.getUserName());
        User user = userService.createOrGet(request.getUser());
        Account entity = request.toEntity(user);
        Account result = repository.save(entity);
        log.info("Successfully created Account by request - {}, result - {}", request, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getById(final UUID id) {
        log.info("Retrieving Account with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Account account = repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Account with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving Account with id - {}, result - {} ", id, account);
        return account;
    }

    @Override
    public Account getByUserName(final String userName) {
        log.info("Retrieving Account with userName - {} ", userName);
        Assert.notNull(userName, "Id cannot be null");
        Account account = repository.findByUserName(userName)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Account with userName - %s not exists", userName), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving Account with userName - {}, result - {} ", userName, account);
        return account;
    }

    @Override
    @Transactional
    public Account update(final UUID id, final UpdateAccountRequest request) {
        log.info("Updating Account with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Assert.notNull(request, "Request must not be null");
        Account result;
        Account entity = getEntity(id);
        if (!entity.getUserName().equals(request.getUserName())) {
            assertNotExists(request.getUserName());
        }
        User user = userService.update(entity.getUser().getId(), request.getUser());
        entity = request.toEntity(entity, user);
        result = repository.save(entity);
        log.info("Successfully updated Account with id - {}, result - {} ", id, result);
        return result;
    }

    @Override
    @Transactional
    public void delete(final UUID id, final boolean deleteFromDb) {
        log.info("Deleting Account with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Account account = getEntity(id);
        if (deleteFromDb) {
            repository.delete(account);
            log.info("Successfully deleted Account with id - {} from db", id);
        } else {
            account.setStatus(ModelStatus.DELETED);
            repository.save(account);
            log.info("Successfully soft deleted Account with id - {} ", id);
        }
    }

    @Override
    @Transactional
    public void inactivateAccount(final UUID id) {
        log.info("Inactivation Account with id - {} ", id);
        Assert.notNull(id, "Id cannot be null");
        Account account = getEntity(id);
        account.setActive(false);
        repository.save(account);
        log.info("Successfully inactivated Account with id - {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<Account> search(final SearchProperties searchProperties,
                                     final int page,
                                     final int size,
                                     final AccountOrderByOption orderBy) {
        log.info("Searching Account by request - {}", searchProperties);
        Assert.notNull(searchProperties, "Request cannot be null");
        PageModel<Account> accounts = repository.searchAccounts(searchProperties, page, size, orderBy);
        log.info("Successfully retrieved Account - {}", accounts);
        return new PageModel<>(accounts.getItems(), accounts.getTotalCount());
    }

    private Account getEntity(final UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("Account with id - %s not exists", id), ErrorCode.NOT_EXISTS_EXCEPTION));
    }

    private void assertNotExists(final String userName) {
        if (repository.existsByUserName(userName)) {
            throw new RecordConflictException(String
                    .format("Account with userName - %s already exists",
                            userName), ErrorCode.EXISTS_EXCEPTION);
        }
    }
}
