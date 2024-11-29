package com.tigran.api.domain.model.common.search;

import com.tigran.api.domain.model.common.page.SortOption;
import com.tigran.api.domain.model.common.validate.ValidatableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 7:57 PM
 */
@Getter
@Setter
public class DeviceSearchProperties extends ValidatableRequest {

    private String searchText;
    private DeviceSortingOption deviceSortingOption;
    private SortOption sortOrderBy;
}
