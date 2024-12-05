package com.tigran.oauth.repository.email.invitation;

import com.tigran.oauth.domain.entity.email.invitation.EmailInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 3:15 PM
 */
@Repository
public interface EmailInvitationRepository extends JpaRepository<EmailInvitation, UUID> {

    Optional<EmailInvitation> findByEmail(final String email);
    
    boolean existsByEmail(final String email);
    
    void deleteByEmail(final String email);
}
