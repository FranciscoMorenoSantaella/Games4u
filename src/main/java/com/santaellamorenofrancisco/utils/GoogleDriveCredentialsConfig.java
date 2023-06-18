package com.santaellamorenofrancisco.utils;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;

public class GoogleDriveCredentialsConfig {
    private static final String CREDENTIALS_FILE_PATH = "classpath:games4u-backend-f4ea2133dc38.json";

    public static GoogleCredentials getCredentials() throws IOException {
        return GoogleCredentials.fromStream(
                GoogleDriveCredentialsConfig.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH)
        );
    }
}




