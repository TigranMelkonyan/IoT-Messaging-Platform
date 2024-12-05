package com.tigran.oauth.domain.entity.email.invitation;

import com.tigran.oauth.domain.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 2:10 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailInvitation extends AuditableBaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    private boolean isUsed;
}
