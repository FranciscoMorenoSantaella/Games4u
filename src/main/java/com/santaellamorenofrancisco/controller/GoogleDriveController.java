package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GoogleDriveController {

    private final Drive drive;

    @Autowired
    public GoogleDriveController(Drive drive) {
        this.drive = drive;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Upload the file to Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        file.transferTo(tempFile);
        FileContent mediaContent = new FileContent(file.getContentType(), tempFile);
        File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        // Delete the temporary file
        tempFile.delete();

        return "File uploaded successfully. ID: " + uploadedFile.getId();
    }
}


