package com.tigran.oauth.model.entity.common.audit;

import com.tigran.oauth.model.entity.common.audit.listner.AuditListener;
import com.tigran.oauth.model.entity.common.base.AbstractDomainEntity;
import com.tigran.oauth.model.entity.common.base.ModelStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:55 PM
 */
@EntityListeners(AuditListener.class)
@Getter
@Setter
@MappedSuperclass
public class AuditableBaseEntity extends AbstractDomainEntity {

    @CreatedDate
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @LastModifiedDate
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModelStatus status = ModelStatus.ACTIVE;

}
