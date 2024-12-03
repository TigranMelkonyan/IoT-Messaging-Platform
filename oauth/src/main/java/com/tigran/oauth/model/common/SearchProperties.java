package com.tigran.oauth.model.common;

import com.tigran.oauth.model.entity.common.base.ModelStatus;
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
    private SortOption sortBy;
    private int page;
    private int size;
}
