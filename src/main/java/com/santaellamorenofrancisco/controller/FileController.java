package com.santaellamorenofrancisco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.santaellamorenofrancisco.model.File;
import com.santaellamorenofrancisco.model.FileMessage;
import com.santaellamorenofrancisco.service.FileService;
import com.santaellamorenofrancisco.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/file")
public class FileController {
	// Inyectamos el servicio
	
		@Autowired
		FileService service;

		private static final String PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)";

		/**
		 * Metodo que sirve para guardar Files en la carpeta root y guardar los datos de
		 * los Files en la base de datos
		 * 
		 * @param files es el archivo que vamos a guardar
		 * @return una respuesta 200 con un mensaje diciendo que nuestro archivo con su
		 *         nombre original se ha subido correctamente o una respuesta 400 si ha
		 *         habido algun error
		 */
		@PostMapping("/upload")
		public ResponseEntity<FileMessage> uploadFiles(@RequestParam("files") MultipartFile files,
				@RequestParam("game_id") Long game_id) {
			String message = "";
			try {

				Pattern pattern = Pattern.compile(PATTERN);
				Matcher matcher = pattern.matcher(files.getOriginalFilename());

				if (matcher.matches()) {
					String uniquename = FileUtils.uniqueFileName();
					service.save(files, uniquename);
					service.saveDatabase(files, uniquename, game_id);
					message = "El archivo:" + files.getOriginalFilename();
					return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));

				} else {
					message = "El archivo que has subido no tiene una extension valida";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
				}
	
			} catch (Exception e) {
				message = "Fallo al subir los archivos";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
			}
		}

		/**
	
	
		/**
		 * Metodo que sirve para buscar un filen en especifico buscandolo por su nombre
		 * unico
		 * 
		 * @param filename es el nombre unico que vamos a usar para buscar el archivo en
		 *                 concreto
		 * @return devuelve una respuesta 200 con el archivo y si no ha sido valida la
		 *         peticion una respuesta 400
		 */
		@GetMapping("/files/{filename:.+}")
		public ResponseEntity<Resource> getFile(@PathVariable String filename) {
			Resource file = service.load(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		}

		/**
		 * Metodo que sirve para borrar una filen por su nombre unico
		 * 
		 * @param filename es el nombre unico del archivo
		 * @return devuelve una respuesta 200 en la que pone que el archivo se ha
		 *         borrado correctamente si la peticion no es valida devuelve una
		 *         respuesta 400 en la que pone que no se ha podido borrar el archivo
		 */
		@DeleteMapping("/delete/{filename:.+}")
		public ResponseEntity<FileMessage> deleteFile(@PathVariable String filename) {
			String message = "";
			try {
				message = service.deleteFile(filename);
				return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
			} catch (Exception e) {

				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
			}
		}

		
		/**
		 * Metodo que trae la informacion de las filenes desde la base de datos
		 * @return devuelve una lista de todas las filenes de la base de datos
		 */
		@GetMapping("/filesfromdatabase")
		public ResponseEntity<List<File>> getFilesFromDatabase() {
			try {
				List<File> filelist = service.getFilesFromDatabase();
				return new ResponseEntity<List<File>>(filelist, new HttpHeaders(), HttpStatus.OK);

			} catch (Exception e) {
				List<File> filelist = new ArrayList<File>();
				return new ResponseEntity<List<File>>(filelist, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		}
		
		
		@CrossOrigin(origins = "http://localhost:8080")
		@GetMapping("imgurl/{id}")
		public ResponseEntity<File> getFileByGameId(@PathVariable Long id) {
			try {
				File file = service.getFileByGameId(id);
				return new ResponseEntity<File>(file, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {

				return new ResponseEntity<File>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		}
}