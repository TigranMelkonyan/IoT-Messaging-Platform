package com.tigran.api.repository.device.custom;

import com.tigran.api.domain.entity.common.base.ModelStatus;
import com.tigran.api.domain.entity.device.Device;
import com.tigran.api.domain.model.common.page.PageModel;
import com.tigran.api.domain.model.common.search.device.DeviceSearchProperties;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 4:14 PM
 */
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public PageModel<Device> searchDevices(
            final DeviceSearchProperties searchProperties,
            final ModelStatus status,
            final int page,
            final int size) {
        TypedQuery<Device> query = createQuery(searchProperties, "d", Device.class, status, false);
        TypedQuery<Long> countQuery = createQuery(searchProperties, "count(d)", Long.class, status, true);
        query.setFirstResult(page);
        query.setMaxResults(size);
        List<Device> devices = query.getResultList();
        return new PageModel<>(devices, countQuery.getSingleResult());
    }

    private <T> TypedQuery<T> createQuery(
            final DeviceSearchProperties searchProperties,
            final String selectCondition,
            final Class<T> type,
            final ModelStatus status,
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
        appliedParams.put("status", status);
        String sortOrder = "";
        if (Objects.nonNull(searchProperties.getSortOrderBy())) {
            sortOrder = searchProperties.getSortOrderBy().getValue();
        }
        String orderByClause = " order by d.createdOn desc";
        if (Objects.nonNull(searchProperties.getDeviceSortingOption())) {
            String sortField = switch (searchProperties.getDeviceSortingOption()) {
                case MAC_ADDRESS -> "d.macAddress";
                case NAME -> "d.name";
                case CREATED_ON -> "d.createdOn";
            };
            orderByClause = " order by " + sortField + " " + sortOrder;
            if (count) {
                queryString += " group by " + sortField;
            }
        }
        queryString += orderByClause;
        TypedQuery<T> query = entityManager.createQuery(queryString, type);
        appliedParams.forEach(query::setParameter);
        return query;
    }
}
