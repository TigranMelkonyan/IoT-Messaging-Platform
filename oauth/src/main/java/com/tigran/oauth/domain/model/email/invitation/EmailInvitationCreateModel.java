package com.tigran.oauth.domain.model.email.invitation;

import com.tigran.oauth.domain.entity.email.invitation.EmailInvitation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 3:10 PM
 */
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class EmailInvitationCreateModel {

    private String email;
    private String token;
    private LocalDateTime expirationTime;
    private boolean isUsed;

    public EmailInvitation toEntity() {
        EmailInvitation invitation = new EmailInvitation();
        invitation.setEmail(email);
        invitation.setToken(token);
        invitation.setExpirationTime(expirationTime);
        invitation.setUsed(isUsed);
        return invitation;
    }
}
