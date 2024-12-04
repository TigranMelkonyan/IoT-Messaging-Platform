package com.tigran.api.application.dto.notificatio.template;

import com.tigran.api.domain.model.common.validate.ValidatableRequest;
import com.tigran.api.domain.model.common.validate.ZoneIdValidator;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.model.entity.notification.template.TemperatureUnit;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:21 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateNotificationTemplateRequest extends ValidatableRequest {

    @Min(value = -40)
    private double tempMinThreshold;

    @Max(value = 40)
    private double tempMaxThreshold;

    @Min(value = 0)
    private double humMinThreshold;

    @Max(value = 100)
    private double humMaxThreshold;

    @Min(value = 0)
    @Max(value = 100)
    private int batteryThreshold;

    @Enumerated(value = EnumType.STRING)
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;

    @Column(columnDefinition = "Time")
    private LocalTime sendStartTime;

    @Column(columnDefinition = "Time")
    private LocalTime sendEndTime;

    @ZoneIdValidator(message = "required")
    private ZoneId zoneId;

    public NotificationTemplate toEntity() {
        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setTempMinThreshold(this.tempMinThreshold);
        notificationTemplate.setTempMaxThreshold(this.tempMaxThreshold);
        notificationTemplate.setHumMinThreshold(this.humMinThreshold);
        notificationTemplate.setHumMaxThreshold(this.humMaxThreshold);
        notificationTemplate.setZoneId(this.zoneId);
        notificationTemplate.setSendStartTime(this.sendStartTime);
        notificationTemplate.setSendEndTime(this.sendEndTime);
        notificationTemplate.setTemperatureUnit(this.temperatureUnit);
        notificationTemplate.setBatteryThreshold(this.batteryThreshold);
        return notificationTemplate;
    }
}
