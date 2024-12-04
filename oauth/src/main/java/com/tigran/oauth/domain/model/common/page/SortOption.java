package com.tigran.oauth.domain.model.common.page;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:02 PM
 */
@Getter
@RequiredArgsConstructor
public enum SortOption {

    ASC("asc"),
    DESC("desc");

    private final String value;
}
