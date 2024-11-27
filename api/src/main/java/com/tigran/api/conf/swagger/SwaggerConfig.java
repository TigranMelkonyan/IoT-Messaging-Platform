package com.tigran.api.conf.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 8:41 PM
 */
@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP, name = "Bearer",
        scheme = "bearer", in = SecuritySchemeIn.HEADER,
        bearerFormat = "Bearer ")
public class SwaggerConfig {

    private static final String VERSION = "v1";
    private static final String DISPLAY_NAME = "IoT Messaging Platform";
    private static final String DESCRIPTION = "Centralized platform for data distribution and notifications";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .addOpenApiCustomizer(openApi -> openApi
                        .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                        .setInfo(new Info()
                                .version(VERSION)
                                .title(DISPLAY_NAME)
                                .description(DESCRIPTION)))
                .pathsToMatch("/**")
                .packagesToScan("com.tigran.api")
                .build();
    }
}
