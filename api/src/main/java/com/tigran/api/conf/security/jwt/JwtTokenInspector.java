package com.tigran.api.conf.security.jwt;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 11/22/24
 * Time: 8:24 PM
 */
@Component
public class JwtTokenInspector implements OpaqueTokenIntrospector {

    private static final String JWT_ALGORITHM = "HmacSHA256";

    private final String secret;

    public JwtTokenInspector(
            @Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        try {
            Jwt jwt = getJwtDecoder().decode(token);
            checkJwtClaims(jwt);
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (jwt.getClaim("authorities") != null) {
                String role = jwt.getClaim("authorities");
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new DefaultOAuth2AuthenticatedPrincipal(
                    jwt.getSubject(),
                    jwt.getClaims(),
                    authorities

            );
        } catch (JwtException e) {
            throw new OAuth2IntrospectionException("Invalid JWT token", e);
        }
    }

    public JwtDecoder getJwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(), JWT_ALGORITHM);
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    private void checkJwtClaims(Jwt jwt) {
        List<String> requiredClaims = Arrays.asList(
                JwtClaim.UID,
                JwtClaim.SUB,
                JwtClaim.EXPIRATION,
                JwtClaim.NAME
        );
        if (!jwt.getClaims().keySet().containsAll(requiredClaims)) {
            throw new OAuth2IntrospectionException("JWT token doesn't contain all required claims");
        }
    }
}
