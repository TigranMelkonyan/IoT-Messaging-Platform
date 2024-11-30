package com.tigran.api.adapter.inbound.messaging;

import com.tigran.api.application.usecase.DeviceDataProcessingService;
import com.tigran.api.domain.model.event.cosnumer.ConsumerEvent;
import com.tigran.api.domain.model.rabbitmq.DevicePublishData;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 5:09 PM
 */
@Component
@Log4j2
public class RabbitMQListener {

    private final DeviceDataProcessingService deviceDataProcessingService;

    public RabbitMQListener(final DeviceDataProcessingService deviceDataProcessingService) {
        this.deviceDataProcessingService = deviceDataProcessingService;
    }

    @RabbitListener(queues = "${rabbitmq.device.data.queue}")
    @Async
    public void handle(final ConsumerEvent<DevicePublishData> event) {
        if (Objects.isNull(event) || Objects.isNull(event.eventType())) {
            log.error("Invalid event detected - {}", event);
        }
        switch (event.eventType()) {
            case DATA_PUBLISH:
                deviceDataProcessingService.processDeviceData(event.data());
                break;
            case TRIP_START:
            case TRIP_END:
            default:
                log.warn("UNDEFINED EVENT HANDLED: - {} ", event);
        }
    }
}
