package com.tigran.api.domain.model.entity.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:55 PM
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    @GeneratedValue
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID id;
}
