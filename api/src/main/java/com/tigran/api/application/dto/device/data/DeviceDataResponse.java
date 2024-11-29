package com.tigran.api.application.dto.device.data;

import com.tigran.api.domain.model.entity.device.data.DeviceData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 5:00 PM
 */
@Data
@AllArgsConstructor
public class DeviceDataResponse {

    private Instant timestamp;
    private Double temperature;
    private Double humidity;
    private Integer batteryLevel;

    public static DeviceDataResponse from(final DeviceData deviceData) {
        return new DeviceDataResponse(
                deviceData.getTimestamp(), deviceData.getTemperature(),
                deviceData.getHumidity(), deviceData.getBatteryLevel());
    }
}
