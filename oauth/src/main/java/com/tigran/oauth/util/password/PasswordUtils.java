package com.tigran.oauth.util.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 11:47 AM
 */
public final class PasswordUtils {

    private PasswordUtils() {
    }

    static BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder(10, new SecureRandom());

    public static String encode(String password) {
        return bCryptPasswordEncoder.encode(password);

    }

    public static boolean isPasswordMatch(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);

    }
}
