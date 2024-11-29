package com.tigran.api.domain.model.event.cosnumer;

import com.tigran.api.domain.model.event.cosnumer.data.ConsumerData;
import com.tigran.api.domain.model.event.type.EventType;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 6:19 PM
 */
public record ConsumerEvent<T extends ConsumerData>(EventType eventType, T data) {
}
