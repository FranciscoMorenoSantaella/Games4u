package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.santaellamorenofrancisco.utils.FileUtils;

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
        if (file != null && !file.isEmpty()) {
            // Obtiene el nombre original del archivo
            String originalFilename = file.getOriginalFilename();
            
            // Crea los metadatos del archivo
            File fileMetadata = new File();
            fileMetadata.setName(FileUtils.uniqueFileName());
            
            // Crea un archivo temporal para transferir el contenido del archivo subido
            java.io.File tempFile = java.io.File.createTempFile("temp", null);
            file.transferTo(tempFile);
            
            // Crea el contenido del archivo para la subida
            FileContent mediaContent = new FileContent(file.getContentType(), tempFile);
            
            // Crea el archivo en Google Drive
            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            // Borra el archivo temporal
            tempFile.delete();

            return "File uploaded successfully. ID: " + uploadedFile.getId() + ", Name: " + uploadedFile.getName();
        } else {
            return "No file selected for upload.";
        }
    }
}
