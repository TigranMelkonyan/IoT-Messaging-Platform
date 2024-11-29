package com.tigran.api.application.dto.device;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 5:07 PM
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
