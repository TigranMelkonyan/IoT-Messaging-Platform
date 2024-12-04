package com.tigran.api.domain.model.entity.notification.template;

import com.tigran.api.domain.model.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 6:28 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class NotificationTemplate extends AuditableBaseEntity {
    
    private double tempMinThreshold;
    private double tempMaxThreshold;
    private double humMinThreshold;
    private double humMaxThreshold;
    private int batteryThreshold;

    @Enumerated(value = EnumType.STRING)
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;
    
    @Column(columnDefinition = "Time")
    private LocalTime sendStartTime;

    @Column(columnDefinition = "Time")
    private LocalTime sendEndTime;

    private ZoneId zoneId;
}
