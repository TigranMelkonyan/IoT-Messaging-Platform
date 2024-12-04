package com.tigran.oauth.domain.model.common.page;

import com.tigran.oauth.domain.entity.common.base.ModelStatus;
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
