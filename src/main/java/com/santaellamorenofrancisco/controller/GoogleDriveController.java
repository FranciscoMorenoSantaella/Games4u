package com.santaellamorenofrancisco.controller;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.santaellamorenofrancisco.model.FileMessage;
import com.santaellamorenofrancisco.service.FileService;
import com.santaellamorenofrancisco.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class GoogleDriveController {
	
	@Autowired
	FileService service;
	private static final String PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)";

    private final Drive drive;
    private static final String FOLDER_NAME = "g4"; // Nombre de la carpeta

    @Autowired
    public GoogleDriveController(Drive drive) {
        this.drive = drive;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("files") MultipartFile files, @RequestParam("game_id") Long game_id,@RequestParam("executable") Boolean executable) throws IOException {
        // Buscar la carpeta por nombre
        String folderId = getFolderIdByName(FOLDER_NAME);
        if (folderId == null) {
            return "Carpeta no encontrada.";
        }
        
    	try {

			Pattern pattern = Pattern.compile(PATTERN);
			Matcher matcher = pattern.matcher(files.getOriginalFilename());

			if (matcher.matches()) {
				  File fileMetadata = new File();
			        fileMetadata.setName(FileUtils.uniqueFileName());
			        fileMetadata.setParents(Collections.singletonList(folderId));
			        
			        java.io.File tempFile = java.io.File.createTempFile("temp", null);
			        files.transferTo(tempFile);
			        FileContent mediaContent = new FileContent(files.getContentType(), tempFile);
			        File uploadedFile = drive.files().create(fileMetadata, mediaContent)
			                .setFields("id")
			                .execute();

			        // Delete the temporary file
			        tempFile.delete();
				    String imageUrl = getImageUrl(uploadedFile.getId());
				String uniquename = FileUtils.uniqueFileName();
				System.out.println(uniquename);
				service.saveDatabase(files, uniquename, game_id,executable,imageUrl,uploadedFile.getId());
				System.out.println("Guardo en la base de datos");
		
			      // Upload the file to Google Drive
		      

		        return "File uploaded successfully. ID: " + uploadedFile.getId();

			} else {
				return "Bad file extension";
			}

		} catch (Exception e) {
			return "Error al subir el archivo";
		}
        
        

  
    }
    private String getImageUrl(String fileId) {
        return "https://drive.google.com/uc?export=view&id=" + fileId;
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
