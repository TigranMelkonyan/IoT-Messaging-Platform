package com.tigran.api.infrastructure.conf.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24per
 * Time: 7:33 PM
 */
@EnableJpaRepositories(basePackages = {"com.tigran.api"})
@EntityScan(basePackages = {"com.tigran.api.domain"})
@Configuration
public class PersistenceConfig {
}
