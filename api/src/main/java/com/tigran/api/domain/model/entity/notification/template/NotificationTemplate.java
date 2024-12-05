package com.tigran.api.domain.model.entity.notification.template;

import com.tigran.api.domain.model.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 6:28 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class NotificationTemplate extends AuditableBaseEntity {

    @Column(name = "device_id", columnDefinition = "UUID")
    private UUID deviceId;

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

    private boolean enableNotifications;

    @ElementCollection
    @CollectionTable(name = "user_account_ids", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "user_account_id")
    private Set<UUID> receiversIds = new HashSet<>();
}
