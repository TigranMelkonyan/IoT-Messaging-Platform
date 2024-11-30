package com.tigran.api.domain.port.outbound.websocket;

import com.tigran.api.domain.model.rabbitmq.DevicePublishData;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 2:23 PM
 */
public interface WebSocketPublisher {

    void publishData(final DevicePublishData data);
}
