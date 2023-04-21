package com.santaellamorenofrancisco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.service.GameService;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
public class GameController {
	@Autowired
	GameService service;
	
	/**
	 * Metodo que devuelve una lista de juegos
	 * 
	 * @return Lista de juegos con un codigo 200 o una respuesta 400 si no se ha
	 *         realizado correctamente si devuelve dicha respuesta normalmente sera
	 *         porque no hay productos en la base de datos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping
	public ResponseEntity<List<Game>> getAllGames() {
		try {
			List<Game> gamelist = service.getAllGames();
			return new ResponseEntity<List<Game>>(gamelist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para buscar un juego por su id
	 * 
	 * @param id El id del juego que queremos borrar
	 * @return Un codigo 200 de que la operacion se ha realizado o un codigo 400 si
	 *         ha fallado
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/{id}")
	public ResponseEntity<Game> getGameById(@PathVariable Long id) {
		try {
			Game game = service.getGameById(id);
			return new ResponseEntity<Game>(game, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para borrar un juego por su id
	 * 
	 * @param id El id del juego que queremos borrar
	 * @return Un codigo 200 de que la operacion se ha realizado o un codigo 400 si
	 *         ha fallado
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("/{id}")
	public ResponseEntity<Game> deleteGameById(@PathVariable Long id) {
		try {
			service.deleteGameById(id);
			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para traer una pagina de juegos
	 * @param pagenumber es el número de la página que queremos traer
	 * @param pagesize es el número de elementos que hay en cada página
	 * @return devuelve una pagina de juegos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "getgame/{pagenumber}/{pagesize}", method = RequestMethod.GET)
	public ResponseEntity<Page<Game>> getProductByPage(@PathVariable int pagenumber, @PathVariable int pagesize) {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Page<Game> pagegames = service.getGameByPage(pagenumber, pagesize);
				return new ResponseEntity<Page<Game>>(pagegames, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Page<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Page<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Metodo que devuelve una lista de videojuegos según el nombre
	 * no tiene porque coincidir exactamente lo buscado con el nombre del juego
	 * @param name es el nombre o una aproximacion del nombre que tiene el juego
	 * @return devuelve una lista de 4 juegos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("searchgamebyname/{name}")
	public ResponseEntity<List<Game>> searchGameByName(@PathVariable String name) {
		try {
			List<Game> gamelist = service.searchGameByName(name);
			return new ResponseEntity<List<Game>>(gamelist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getgamebyname/{name}")
	public ResponseEntity<Game> getGameByName(@PathVariable String name) {
		try {
			Game game = service.getGameByName(name);
			return new ResponseEntity<Game>(game, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Game>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
