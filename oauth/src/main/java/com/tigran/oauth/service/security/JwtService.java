package com.tigran.oauth.service.security;

import com.tigran.oauth.conf.security.jwt.JwtClaim;
import com.tigran.oauth.domain.entity.account.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 11:20 AM
 */
@Component
public class JwtService {

    public static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_TYPE_HEADER = "typ";

    private final String jwtSecret;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.token.validity}") long jwtExpiration

    ) {
        this.jwtSecret = jwtSecret;
        this.expiration = jwtExpiration;
    }

    public String createJwt(final Account account) {
        final Key signingKey = new SecretKeySpec(jwtSecret.getBytes(), algorithm.getJcaName());
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaim.UID, account.getId());
        claims.put(JwtClaim.SUB, account.getUser().getEmail());
        claims.put(JwtClaim.EMAIL, account.getUser().getEmail());
        claims.put(JwtClaim.NAME, account.getUserName());
        claims.put(JwtClaim.AUTHORITIES, account.getRole());
        return Jwts.builder()
                .setId(account.getId().toString())
                .setExpiration(Date.from(Instant.now().plusMillis(expiration)))
                .setHeaderParam(TOKEN_TYPE_HEADER, TOKEN_TYPE)
                .addClaims(claims)
                .signWith(algorithm, signingKey)
                .compact();
    }
}
