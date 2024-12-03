package com.tigran.oauth.service.account.impl;

import com.tigran.oauth.model.common.PageModel;
import com.tigran.oauth.model.common.SearchProperties;
import com.tigran.oauth.model.entity.account.Account;
import com.tigran.oauth.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.model.rest.request.account.UpdateAccountRequest;
import com.tigran.oauth.repository.account.AccountRepository;
import com.tigran.oauth.service.account.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account createOrGet(final CreateAccountRequest request) {
        return null;
    }

    @Override
    public Account getById(final UUID id) {
        return null;
    }

    @Override
    public Account update(final UUID id, final UpdateAccountRequest request) {
        return null;
    }

    @Override
    public void delete(final UUID id, final boolean deleteFromDb) {

    }

    @Override
    public PageModel<Account> search(final SearchProperties searchProperties) {
        return null;
    }
}
