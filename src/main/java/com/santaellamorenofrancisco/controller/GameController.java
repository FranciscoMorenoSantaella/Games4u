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

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	GameService service;
	

	@GetMapping
	public ResponseEntity<List<Game>> getAllGames() {
		try {
			List<Game> gamelist = service.getAllGames();
			return new ResponseEntity<List<Game>>(gamelist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Game> getGameById(@PathVariable Long id) {
		try {
			Game game = service.getGameById(id);
			return new ResponseEntity<Game>(game, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Game> deleteGameById(@PathVariable Long id) {
		try {
			service.deleteGameById(id);
			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
