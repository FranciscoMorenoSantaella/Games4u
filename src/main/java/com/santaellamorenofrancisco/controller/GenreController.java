package com.santaellamorenofrancisco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.Genre;
import com.santaellamorenofrancisco.service.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreController {
	@Autowired
	GenreService service;
	

	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenres() {
		try {
			List<Genre> genrelist = service.getAllGenres();
			return new ResponseEntity<List<Genre>>(genrelist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Genre>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
		try {
			Genre genre = service.getGenreById(id);
			return new ResponseEntity<Genre>(genre, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Genre>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
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
