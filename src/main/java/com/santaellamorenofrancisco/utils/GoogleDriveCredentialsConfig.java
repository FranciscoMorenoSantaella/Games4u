package com.santaellamorenofrancisco.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Configuration
public class GoogleDriveCredentialsConfig {
    private static final String CREDENTIALS_FILE_PATH = "classpath:games4u-backend-f4ea2133dc38.json";

    public static GoogleCredentials getCredentials() throws IOException {
        return GoogleCredentials.fromStream(
                GoogleDriveCredentialsConfig.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH)
        );
    }
    
    @Bean
    public Drive drive() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                getClass().getResourceAsStream(CREDENTIALS_FILE_PATH)
        ).createScoped(Collections.singleton(DriveScopes.DRIVE));

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(httpTransport, GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("Games4u")
                .build();

        return drive;
    }
}




