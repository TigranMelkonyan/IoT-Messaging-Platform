package com.tigran.api.domain.entity.common.audit.listner;

import com.tigran.api.domain.entity.common.audit.AuditableBaseEntity;
import com.tigran.api.domain.entity.common.base.ModelStatus;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:56 PM
 */
public class AuditListener extends AuditingEntityListener {

    @PrePersist
    public void setCreatedOn(AuditableBaseEntity audit) {
        audit.setCreatedOn(LocalDateTime.now());
        audit.setStatus(ModelStatus.ACTIVE);
        setUpdatedOn(audit);
    }

    @PreUpdate
    public void setUpdatedOn(AuditableBaseEntity audit) {
        audit.setUpdatedOn(LocalDateTime.now());
        if (audit.getStatus().equals(ModelStatus.DELETED)) {
            audit.setDeletedOn(LocalDateTime.now());
        } else {
            audit.setDeletedOn(null);
        }
    }
}