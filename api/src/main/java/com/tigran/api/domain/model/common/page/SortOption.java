package com.tigran.api.domain.model.common.page;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 12:27 PM
 */
@Getter
@RequiredArgsConstructor
public enum SortOption {

    ASC("asc"),
    DESC("desc");

    private final String value;
}
