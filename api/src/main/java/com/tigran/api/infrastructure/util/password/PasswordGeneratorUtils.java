package com.tigran.api.infrastructure.util.password;

import java.security.SecureRandom;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 8:29 PM
 */
public final class PasswordGeneratorUtils {

    private PasswordGeneratorUtils() {
    }

    public static String generateRandomPassword(final int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrst" +
                "uvwxyz0123456789!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return String.valueOf(password);
    }
}
