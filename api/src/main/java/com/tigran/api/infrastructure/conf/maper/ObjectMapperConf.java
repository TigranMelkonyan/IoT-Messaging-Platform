package com.tigran.api.infrastructure.conf.maper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 3:24 PM
 */
@Configuration
public class ObjectMapperConf {

    @Bean
    public ObjectMapper setUp() {
        return new ObjectMapper();
    }
}
