package com.tigran.api.domain.model.rabbitmq;

import com.tigran.api.domain.model.event.cosnumer.data.ConsumerData;
import lombok.Data;

import java.time.Instant;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 6:18 PM
 */
@Data
public class DevicePublishData implements ConsumerData {

    private String macAddress;
    private Instant timestamp;
    private Double temperature;
    private Double humidity;
    private Integer batteryLevel;
}
