package com.tigran.oauth.domain.entity.user;

import com.tigran.oauth.domain.entity.common.audit.AuditableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 1:15 PM
 */
@Entity
@Table(name = "oauth_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditableBaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 20)
    private String phone;
}
