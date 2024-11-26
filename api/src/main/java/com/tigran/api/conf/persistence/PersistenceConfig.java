package com.tigran.api.conf.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:33 PM
 */
@EnableJpaRepositories(basePackages = {"com.tigran.api.repository"})
@EntityScan(basePackages = {"com.tigran.api.domain.entity"})
@Configuration
public class PersistenceConfig {
}
