package com.santaellamorenofrancisco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.Genre;
import com.santaellamorenofrancisco.service.GenreService;

@RestController
@RequestMapping("/genre")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
public class GenreController {
	@Autowired
	GenreService service;

	/**
	 * Metodo que devuelve un listado de todos los generos
	 * @return un listado de todos los generos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenres() {
		try {
			List<Genre> genrelist = service.getAllGenres();
			return new ResponseEntity<List<Genre>>(genrelist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Genre>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que devuelve un genero segun su id 
	 * @param id es el id del genero que se va a buscar
	 * @return un genero
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/{id}")
	public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
		try {
			Genre genre = service.getGenreById(id);
			return new ResponseEntity<Genre>(genre, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Genre>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para borrar un genero segun su id
	 * @param id es el id del genero que queremos borrar
	 * @return devuelve solo la respuesta del codigo de todo ha ido bien o la de no ha ido bien
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("/{id}")
	public ResponseEntity<Genre> deleteGenreById(@PathVariable Long id) {
		try {
			service.deleteGenreById(id);
			return new ResponseEntity<Genre>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Genre>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
