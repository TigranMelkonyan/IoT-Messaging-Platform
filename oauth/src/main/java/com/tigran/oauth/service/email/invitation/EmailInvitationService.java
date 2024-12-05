package com.tigran.oauth.service.email.invitation;

import com.tigran.oauth.domain.entity.email.invitation.EmailInvitation;
import com.tigran.oauth.domain.model.email.invitation.EmailInvitationCreateModel;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 3:12 PM
 */
public interface EmailInvitationService {

    EmailInvitation create(final EmailInvitationCreateModel request);

    EmailInvitation getByEmail(final String email);
}
