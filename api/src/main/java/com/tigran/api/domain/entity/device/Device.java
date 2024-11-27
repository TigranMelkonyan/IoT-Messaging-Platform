package com.tigran.api.domain.entity.device;

import com.tigran.api.domain.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 11/25/24
 * Time: 5:46 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(indexes = @Index(columnList = "name"))
public class Device extends AuditableBaseEntity {

    @Column(nullable = false, unique = true, length = 16)
    private String macAddress;

    @Column(nullable = false, unique = true, length = 12)
    private String name;

    @Column(length = 50)
    private String note;
}
