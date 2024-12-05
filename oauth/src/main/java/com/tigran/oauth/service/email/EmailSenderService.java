package com.tigran.oauth.service.email;

/**
 * Created by Tigran Melkonyan
 * Date: 12/5/24
 * Time: 2:19 PM
 */
public interface EmailSenderService {
    
    void send(final String email, final String title, final String text);
}
