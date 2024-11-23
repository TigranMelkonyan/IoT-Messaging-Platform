package com.tigran.iot_messaging_platform.conf.security;

import com.tigran.iot_messaging_platform.conf.security.jwt.JwtTokenInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.SecureRandom;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 9:07 PM
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenInspector jwtTokenInspector;

    @Autowired
    public SecurityConfig(final JwtTokenInspector jwtTokenInspector) {
        this.jwtTokenInspector = jwtTokenInspector;
    }


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(
//                        authorizeHttpRequests -> authorizeHttpRequests
//                                .requestMatchers(
//                                        "/v3/api-docs/**",
//                                        "/swagger*/**",
//                                        "/webjars/**")
//                                .permitAll()
//                                .anyRequest().authenticated())
//                .csrf(AbstractHttpConfigurer::disable)
//                .oauth2ResourceServer(resourceServer -> resourceServer
//                        .opaqueToken(token -> token.introspector(jwtTokenInspector))).build();
        //TODO remove after development
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(c -> {
                    c.ignoringRequestMatchers("/white");
                });
        return http.build();
    }
}
