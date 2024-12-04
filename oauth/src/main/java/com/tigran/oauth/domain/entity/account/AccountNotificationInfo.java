package com.tigran.oauth.domain.entity.account;

import com.tigran.oauth.domain.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 1:18 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AccountNotificationInfo extends AuditableBaseEntity {
    
    @OneToOne(mappedBy = "notificationInfo")
    public Account account;

    private boolean emailNotificationEnabled;
    private boolean smsNotificationEnabled;
    private boolean pushNotificationEnabled;
}
