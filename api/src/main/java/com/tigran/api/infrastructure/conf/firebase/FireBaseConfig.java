package com.tigran.api.infrastructure.conf.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 11/30/24
 * Time: 8:21 PM
 */
@Configuration
public class FireBaseConfig {

    @Bean
    public FirebaseApp getDefault() throws IOException {
        FirebaseOptions options = FirebaseOptions
                .builder()
                .setCredentials(GoogleCredentials
                        .fromStream(Objects.requireNonNull(getClass()
                                .getResourceAsStream("/firebase/firebase_key.json"))))
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
