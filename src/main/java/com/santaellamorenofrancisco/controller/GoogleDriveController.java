package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.santaellamorenofrancisco.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class GoogleDriveController {

    private final Drive drive;

    @Autowired
    public GoogleDriveController(Drive drive) {
        this.drive = drive;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Create the file metadata
            File fileMetadata = new File();
            fileMetadata.setName(FileUtils.uniqueFileName());

            // Create a temporary file to store the uploaded file
            Path tempFilePath = Files.createTempFile("temp", null);
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Upload the file to Google Drive
            File uploadedFile = drive.files().create(fileMetadata, new FileContent(file.getContentType(), tempFilePath.toFile()))
                    .setFields("id")
                    .execute();

            // Delete the temporary file
            Files.delete(tempFilePath);

            return "File uploaded successfully. ID: " + uploadedFile.getId() + ", Name: " + uploadedFile.getName();
        } else {
            return "No file selected for upload.";
        }
    }
}
