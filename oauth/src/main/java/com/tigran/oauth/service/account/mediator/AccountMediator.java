package com.tigran.oauth.service.account.mediator;

import com.tigran.oauth.domain.entity.email.invitation.EmailInvitation;
import com.tigran.oauth.domain.model.common.role.Role;
import com.tigran.oauth.domain.model.email.invitation.EmailInvitationCreateModel;
import com.tigran.oauth.domain.model.exception.ErrorCode;
import com.tigran.oauth.domain.model.exception.RecordConflictException;
import com.tigran.oauth.domain.model.rest.request.account.CreateAccountRequest;
import com.tigran.oauth.service.account.AccountService;
import com.tigran.oauth.service.email.impl.EmailSenderServiceImpl;
import com.tigran.oauth.service.email.invitation.EmailInvitationService;
import com.tigran.oauth.service.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 12:56 PM
 */
@Service
@Log4j2
public class AccountMediator {

    private final String secretKey;
    private final String registerLink;
    private final JwtService jwtService;
    private final AccountService accountService;
    private final EmailSenderServiceImpl emailSenderService;
    private final EmailInvitationService emailInvitationService;

    public AccountMediator(
            @Value("${jwt.secret}") final String secretKey,
            @Value("${app.register-link}") final String registerLink,
            final JwtService jwtService,
            final AccountService accountService,
            final EmailSenderServiceImpl emailSenderService,
            final EmailInvitationService emailInvitationService) {
        this.secretKey = secretKey;
        this.registerLink = registerLink;
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.emailSenderService = emailSenderService;
        this.emailInvitationService = emailInvitationService;
    }

    public void sendAccountRegistrationInvitation(final String email, final Role role) {
        String token = jwtService.generateInvitationToken(email, role);
        String registrationLink = registerLink + token;
        String message = "Hello, please register using the following link: " + registrationLink;
        EmailInvitationCreateModel createModel = new EmailInvitationCreateModel(
                email, token, LocalDateTime.now(), true);
        emailInvitationService.create(createModel);
        emailSenderService.send(email, "Registration Invitation", message);
    }

    public void registerAccount(final String token, final CreateAccountRequest request) {
        try {
            log.info("Registering account with - {} email ", request.getUser().getEmail());
            Claims claims = get(token);
            String email = claims.getSubject();
            String type = claims.get("type", String.class);
            String role = claims.get("role", String.class);
            EmailInvitation invitation = emailInvitationService.getByEmail(token);
            if (invitation.isUsed() || invitation.getExpirationTime().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Token is no longer valid");
            }
            if (!"invitation".equals(type)) {
                throw new RecordConflictException("Invalid token", ErrorCode.INVALID_CREDENTIALS);
            }
            if (!email.equals(request.getUser().getEmail())) {
                throw new RecordConflictException("Email mismatch", ErrorCode.INVALID_CREDENTIALS);
            }
            request.setRole(Role.valueOf(role));
            request.setVerified(true);
            accountService.create(request);
            log.info("Successfully registered account with - {} email ", request.getUser().getEmail());
        } catch (JwtException e) {
            log.warn("Jwt exception - {}", e.getMessage());
        }
    }

    private Claims get(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
