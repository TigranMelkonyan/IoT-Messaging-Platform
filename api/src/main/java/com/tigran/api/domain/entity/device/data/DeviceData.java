package com.tigran.api.domain.entity.device.data;

import com.tigran.api.domain.entity.common.audit.AuditableBaseEntity;
import com.tigran.api.domain.entity.device.Device;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 5:50 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DeviceData extends AuditableBaseEntity {

    @ManyToOne
    private Device device;

    private Instant timestamp;
    private Double temperature;
    private Double humidity;
    private Integer batteryLevel;
}
