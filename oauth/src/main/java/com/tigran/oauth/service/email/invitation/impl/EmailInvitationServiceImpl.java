package com.tigran.oauth.service.email.invitation.impl;

import com.tigran.oauth.domain.entity.email.invitation.EmailInvitation;
import com.tigran.oauth.domain.model.email.invitation.EmailInvitationCreateModel;
import com.tigran.oauth.domain.model.exception.ErrorCode;
import com.tigran.oauth.domain.model.exception.RecordConflictException;
import com.tigran.oauth.repository.email.invitation.EmailInvitationRepository;
import com.tigran.oauth.service.email.invitation.EmailInvitationService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 3:15 PM
 */
@Service
@Log4j2
public class EmailInvitationServiceImpl implements EmailInvitationService {

    private final EmailInvitationRepository repository;

    public EmailInvitationServiceImpl(final EmailInvitationRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EmailInvitation create(final EmailInvitationCreateModel request) {
        log.info("Creating EmailInvitation by request - {} ", request);
        Assert.notNull(request, "Request must not be null");
        assertNotExistsWithMail(request.getEmail());
        EmailInvitation entity = request.toEntity();
        EmailInvitation result = repository.save(entity);
        log.info("Successfully created EmailInvitation by request - {}, result - {}", request, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public EmailInvitation getByEmail(final String email) {
        log.info("Retrieving EmailInvitation with email - {} ", email);
        Assert.notNull(email, "Id cannot be null");
        EmailInvitation entity = repository.findByEmail(email)
                .orElseThrow(() -> new RecordConflictException(String
                        .format("EmailInvitation with email - %s not exists", email), ErrorCode.NOT_EXISTS_EXCEPTION));
        log.info("Successfully retrieving EmailInvitation with email - {}, result - {} ", email, entity);
        return entity;
    }
    
    private void assertNotExistsWithMail(final String email) {
        if (repository.existsByEmail(email)) {
            throw new RecordConflictException(String
                    .format("EmailInvitation with email - %s already exists",
                            email), ErrorCode.EXISTS_EXCEPTION);
        }
    }
}
