package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.santaellamorenofrancisco.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Obtain the original filename
            String originalFilename = file.getOriginalFilename();
            
            // Create the file metadata
            File fileMetadata = new File();
            fileMetadata.setName(FileUtils.uniqueFileName());
            
            // Create a temporary file to store the uploaded file
            Path tempFilePath = Files.createTempFile("temp", null);
            
            // Copy the content of the uploaded file to the temporary file
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Create a java.io.File object from the temporary file
            java.io.File tempFile = tempFilePath.toFile();
            
            // Create the file content with the specified type and File object
            FileContent mediaContent = new FileContent(file.getContentType(), tempFile);
            
            // Create the file in Google Drive
            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            System.out.println(uploadedFile);
            
            System.out.println(tempFile);
            
            System.out.println(tempFilePath);
            // Delete the temporary file
            tempFile.delete();

            return "File uploaded successfully. ID: " + uploadedFile.getId() + ", Name: " + uploadedFile.getName();
        } else {
            return "No file selected for upload.";
        }
    }
}
