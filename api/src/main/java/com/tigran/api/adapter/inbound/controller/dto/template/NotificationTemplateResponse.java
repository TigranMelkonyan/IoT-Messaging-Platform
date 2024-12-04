package com.tigran.api.adapter.inbound.controller.dto.template;

import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.model.entity.notification.template.TemperatureUnit;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:51 PM
 */
@Data
public class NotificationTemplateResponse {

    private UUID id;
    private double tempMinThreshold;
    private double tempMaxThreshold;
    private double humMinThreshold;
    private double humMaxThreshold;
    private int batteryThreshold;
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;
    private LocalTime sendStartTime;
    private LocalTime sendEndTime;

    public static NotificationTemplateResponse from(final NotificationTemplate notificationTemplate) {
        NotificationTemplateResponse response = new NotificationTemplateResponse();
        response.setId(notificationTemplate.getId());
        response.setTempMinThreshold(notificationTemplate.getTempMinThreshold());
        response.setTempMaxThreshold(notificationTemplate.getTempMaxThreshold());
        response.setHumMinThreshold(notificationTemplate.getHumMinThreshold());
        response.setHumMaxThreshold(notificationTemplate.getHumMaxThreshold());
        response.setBatteryThreshold(notificationTemplate.getBatteryThreshold());
        response.setTemperatureUnit(notificationTemplate.getTemperatureUnit());
        response.setSendStartTime(notificationTemplate.getSendStartTime());
        response.setSendEndTime(notificationTemplate.getSendEndTime());
        return response;
    }
}
