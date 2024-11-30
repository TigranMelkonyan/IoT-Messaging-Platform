package com.tigran.api.infrastructure.conf.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 1:11 PM
 */
@Configuration
@EnableFeignClients(basePackages = "com.tigran.api.adapter.outbound")
public class FeignConfig {

    @Value("${api.secret.key}")
    private String secret;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("X-API-KEY", secret);
    }
    
}
