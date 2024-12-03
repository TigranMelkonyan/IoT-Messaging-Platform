package com.tigran.oauth.model.entity.account;

import com.tigran.oauth.model.entity.common.audit.AuditableBaseEntity;
import com.tigran.oauth.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 4:26 PM
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends AuditableBaseEntity {

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "not_info_id", referencedColumnName = "id")
    private AccountNotificationInfo notificationInfo;

    @Column(nullable = false, unique = true, length = 100)
    private String userName;

    @Column(nullable = false, length = 100)
    private String password;

    private boolean verified = false;
    private boolean active = true;
}
