package com.tigran.oauth.conf.peristence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 11:41 AM
 */
@EnableJpaRepositories(basePackages = {"com.tigran.oauth.repository"})
@EntityScan(basePackages = {"com.tigran.oauth.domain"})
@Configuration
public class PersistenceConfig {
}
