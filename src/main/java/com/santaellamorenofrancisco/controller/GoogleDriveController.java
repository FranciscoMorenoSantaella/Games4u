package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.santaellamorenofrancisco.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
public class GoogleDriveController {

    private final Drive drive;
    private static final String FOLDER_NAME = "g4"; // Nombre de la carpeta

    @Autowired
    public GoogleDriveController(Drive drive) {
        this.drive = drive;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Buscar la carpeta por nombre
        String folderId = getFolderIdByName(FOLDER_NAME);
        if (folderId == null) {
            return "Carpeta no encontrada.";
        }

        // Upload the file to Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(FileUtils.uniqueFileName());
        fileMetadata.setParents(Collections.singletonList(folderId));

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

    // Buscar el ID de la carpeta por nombre
    private String getFolderIdByName(String folderName) throws IOException {
        String query = "mimeType='application/vnd.google-apps.folder' and name='" + folderName + "'";
        Drive.Files.List request = drive.files().list().setQ(query);
        FileList fileList = request.execute();
        if (fileList.getFiles() != null && !fileList.getFiles().isEmpty()) {
            return fileList.getFiles().get(0).getId();
        }
        return null;
    }
}
