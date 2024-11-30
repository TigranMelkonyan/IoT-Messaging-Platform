package com.tigran.api.adapter.outbound.websocket;

import com.tigran.api.domain.model.rabbitmq.DevicePublishData;
import com.tigran.api.domain.port.outbound.websocket.WebSocketPublisher;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 8:08 PM
 */
@Component
@Log4j2
public class WebSocketPublisherService implements WebSocketPublisher {

    @Override
    public void publishData(final DevicePublishData data) {
        
    }
}
