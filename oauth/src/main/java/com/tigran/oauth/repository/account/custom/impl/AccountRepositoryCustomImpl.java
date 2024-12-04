package com.tigran.oauth.repository.account.custom.impl;

import com.tigran.oauth.domain.model.common.page.AccountOrderByOption;
import com.tigran.oauth.domain.model.common.page.PageModel;
import com.tigran.oauth.domain.model.common.page.SearchProperties;
import com.tigran.oauth.domain.entity.account.Account;
import com.tigran.oauth.repository.account.custom.AccountRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 10:34 AM
 */
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageModel<Account> searchAccounts(
            final SearchProperties searchProperties,
            final int page,
            final int size,
            final AccountOrderByOption orderBy) {
        TypedQuery<Account> query = createQuery(searchProperties, orderBy, "a", Account.class, false);
        TypedQuery<Long> countQuery = createQuery(searchProperties, orderBy, "count(a)", Long.class, true);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Account> account = query.getResultList();
        return countQuery.getResultList().isEmpty() ?
                new PageModel<>(new ArrayList<>(), 0) :
                new PageModel<>(account, countQuery.getSingleResult());
    }

    private <T> TypedQuery<T> createQuery(
            final SearchProperties searchProperties,
            final AccountOrderByOption orderBy,
            final String selectCondition,
            final Class<T> type,
            final boolean count) {
        Map<String, Object> appliedParams = new HashMap<>();
        String prefix = " where ";
        String queryString = " select " + selectCondition + " from Account a ";
        if (Objects.nonNull(searchProperties) && StringUtils.isNotBlank(searchProperties.getSearchText())) {
            queryString = queryString + prefix + "a.userName like :search or a.user.firstName like :search " +
                    "or a.user.lastName like :search or a.user.email like :search ";
            appliedParams.put("search", "%" + searchProperties.getSearchText() + "%");
            prefix = " and ";
        }
        queryString = queryString + prefix + " d.status=:status ";
        appliedParams.put("status", searchProperties.getStatus());
        String sortOrder = "";
        if (Objects.nonNull(searchProperties.getSort())) {
            sortOrder = searchProperties.getSort().getValue();
        }
        if (Objects.nonNull(orderBy) && !count) {
            String sortField = switch (orderBy) {
                case USER_NAME -> "a.userName";
                case FIRST_NAME -> "a.user.firstName";
                case LAST_NAME -> "a.user.lastName";
                case EMAIL -> "a.user.email";
                case CREATED_ON -> "a.createdOn";
            };
            String orderByClause = " order by " + sortField + " " + sortOrder;
            queryString += orderByClause;
        }
        TypedQuery<T> query = entityManager.createQuery(queryString, type);
        appliedParams.forEach(query::setParameter);
        return query;
    }
}
