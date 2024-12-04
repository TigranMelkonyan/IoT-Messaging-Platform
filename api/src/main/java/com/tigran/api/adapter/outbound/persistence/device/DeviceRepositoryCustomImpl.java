package com.tigran.api.adapter.outbound.persistence.device;

import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.DeviceOrderByOption;
import com.tigran.api.domain.model.common.search.SearchProperties;
import com.tigran.api.domain.model.entity.device.Device;
import com.tigran.api.domain.port.outbound.device.DeviceRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:14 PM
 */
@Repository
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageModel<Device> searchDevices(
            final SearchProperties searchProperties,
            final int page,
            final int size,
            final DeviceOrderByOption orderBy) {
        TypedQuery<Device> query = createQuery(searchProperties, orderBy, "d", Device.class, false);
        TypedQuery<Long> countQuery = createQuery(searchProperties, orderBy, "count(d)", Long.class, true);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Device> devices = query.getResultList();
        return countQuery.getResultList().isEmpty() ?
                new PageModel<>(new ArrayList<>(), 0) :
                new PageModel<>(devices, countQuery.getSingleResult());
    }

    private <T> TypedQuery<T> createQuery(
            final SearchProperties searchProperties,
            final DeviceOrderByOption orderBy,
            final String selectCondition,
            final Class<T> type,
            final boolean count) {
        Map<String, Object> appliedParams = new HashMap<>();
        String prefix = " where ";
        String queryString = " select " + selectCondition + " from Device d ";
        if (Objects.nonNull(searchProperties) && StringUtils.isNotBlank(searchProperties.getSearchText())) {
            queryString = queryString + prefix + "d.macAddress like :search or d.name like :search ";
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
                case MAC_ADDRESS -> "d.macAddress";
                case NAME -> "d.name";
                case CREATED_ON -> "d.createdOn";
            };
            String orderByClause = " order by " + sortField + " " + sortOrder;
            queryString += orderByClause;
        }
        TypedQuery<T> query = entityManager.createQuery(queryString, type);
        appliedParams.forEach(query::setParameter);
        return query;
    }
}
