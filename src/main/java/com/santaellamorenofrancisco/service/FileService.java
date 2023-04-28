package com.santaellamorenofrancisco.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.santaellamorenofrancisco.model.File;
import com.santaellamorenofrancisco.repository.FileRepository;
import com.santaellamorenofrancisco.repository.GameRepository;
import com.santaellamorenofrancisco.utils.FileUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
	@Autowired
	FileRepository repository;

	GameRepository gamerepository;

	private final Path root = Paths.get("uploads");

	/**
	 * Metodo que inicializa el directorio en el que se guardan los archivos
	 */
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (Exception e) {
			throw new RuntimeException("No se puede inicializar la carpeta uploads");
		}
	}

	/**
	 * Metodo que guarda el archivo subido en la carpeta de destino
	 * 
	 * @param file       es el archivo que se va a guardar
	 * @param uniquename es nombre unico que se ha autogenerado
	 */
	public void save(MultipartFile file, String uniquename) {
		try {

			Files.copy(file.getInputStream(),
					this.root.resolve(uniquename + "." + FileUtils.getExtension(file.getOriginalFilename())));
		} catch (IOException e) {
			throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
		}
	}

	/**
	 * Metodo que sirve para guardar un File en la base de datos este archivo
	 * tendra un nombre unico
	 * 
	 * @param file       Lo usamoos para saber la extension del archivo
	 * @param uniquename Es el nombre aleatorio y unico que se ha generado y vamos a
	 *                   setear el nombre del File con dicho nombre
	 */
	public void saveDatabase(MultipartFile file, String uniquename, Long game_id) {
		File uploadfile = new File();
		try {
			uploadfile.setUrl(root.resolve(uniquename).toString());
			uploadfile.setOriginalname(file.getOriginalFilename());
			uploadfile.setUniquename(uniquename + "." + FileUtils.getExtension(file.getOriginalFilename()));
			System.out.println(game_id);
			repository.insertFile(uploadfile.getOriginalname(), uploadfile.getUniquename(), uploadfile.getUrl(),
					game_id);
		} catch (Exception e) {
			throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
		}
	}

	/**
	 * Metodo para cargar un archivo
	 * 
	 * @param filename
	 * @return
	 */
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("No se puede leer el archivo ");
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo que sirve para borrar todos los archivos
	 */
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	/**
	 * Metodo que sirve para traer todos los archivos
	 * 
	 * @return devuelve una lista de File
	 */
	public List<File> getAll() {
		return repository.findAll();
	}

	/**
	 * Metodo que trae los Datos de File desde la base de datos
	 * 
	 * @param filename es el nombre aleatorio que tiene el File
	 * @return devuelve un File
	 */
	public List<File> getFilesFromDatabase() {
		return repository.getFilesFromDatabase();
	}

	/**
	 * Metodo que trae todas las rutas relativas de las filenes
	 * 
	 * @return Un stream de rutas en las que se encuentras los File
	 */
	/*public Stream<Path> loadAll() {
		
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (RuntimeException | IOException e) {
			throw new RuntimeException("No se pueden cargar los archivos ");
		}
	}*/

	/**
	 * Borra un archivo especifico pasando su nombre
	 * 
	 * @param filename el nombre aleatorio del archivos
	 * @return String diciendo si se ha borrado o no segun si el resultado es
	 *         favorable o no lo es
	 */
	public String deleteFile(String filename) {
		try {
			Files.deleteIfExists(this.root.resolve(filename));
			return "Borrado";
		} catch (IOException e) {
			e.printStackTrace();
			return "Error Borrando";
		}
	}
	
	/**
	 * Metodo que trae una filen por el id de un producto
	 * @param id es el id del producto del que queremos traer su filen
	 * @return devuelve una filen
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public File getFileByGameId(Long id ) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				File file = repository.getFileByGameId(id);
				if (file != null) {
					return file;
				} else {
					throw new Exception("El Product no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
	}
}
