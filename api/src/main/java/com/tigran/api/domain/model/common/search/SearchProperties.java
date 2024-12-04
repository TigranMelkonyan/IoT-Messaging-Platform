package com.tigran.api.domain.model.common.search;

import com.tigran.api.domain.model.common.page.SortOption;
import com.tigran.api.domain.model.entity.common.base.ModelStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:01 PM
 */
@Getter
@Setter
public class SearchProperties {

    private String searchText;
    private ModelStatus status;
    private SortOption sort;
}
