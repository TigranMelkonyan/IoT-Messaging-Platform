package com.tigran.oauth.domain.model.rest.response.page;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 2:42 PM
 */
@Getter
public class PageResponse<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageResponse(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }
}
