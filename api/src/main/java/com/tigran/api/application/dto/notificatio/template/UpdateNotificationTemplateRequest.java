package com.tigran.api.application.dto.notificatio.template;

import com.tigran.api.domain.model.common.validate.ValidatableRequest;
import com.tigran.api.domain.model.common.validate.ZoneIdValidator;
import com.tigran.api.domain.model.entity.notification.template.NotificationTemplate;
import com.tigran.api.domain.model.entity.notification.template.TemperatureUnit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 7:21 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateNotificationTemplateRequest extends ValidatableRequest {

    @NotNull(message = "required")
    private UUID deviceId;

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

    private LocalTime sendStartTime;
    private LocalTime sendEndTime;

    @ZoneIdValidator(message = "required")
    private ZoneId zoneId;

    private boolean enableNotifications;

    public NotificationTemplate toEntity(final NotificationTemplate notificationTemplate) {
        notificationTemplate.setDeviceId(this.deviceId);
        notificationTemplate.setTempMaxThreshold(this.tempMaxThreshold);
        notificationTemplate.setTempMinThreshold(this.tempMinThreshold);
        notificationTemplate.setHumMaxThreshold(this.humMaxThreshold);
        notificationTemplate.setHumMinThreshold(this.humMinThreshold);
        notificationTemplate.setZoneId(this.zoneId);
        notificationTemplate.setSendStartTime(this.sendStartTime);
        notificationTemplate.setSendEndTime(this.sendEndTime);
        notificationTemplate.setTemperatureUnit(this.temperatureUnit);
        notificationTemplate.setBatteryThreshold(this.batteryThreshold);
        notificationTemplate.setEnableNotifications(this.enableNotifications);
        return notificationTemplate;
    }
}
