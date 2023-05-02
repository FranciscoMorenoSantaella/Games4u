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
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Page<Game>> getGameByPage(@PathVariable int pagenumber, @PathVariable int pagesize) {
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
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "getgamesfromlibrary/{pagenumber}/{pagesize}/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<Page<Game>> getGameFromLibraryPageable(@PathVariable int pagenumber, @PathVariable int pagesize, @PathVariable Long user_id) {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Page<Game> pagegames = service.getGameFromLibraryPageable(pagenumber, pagesize,user_id);
				return new ResponseEntity<Page<Game>>(pagegames, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Page<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Page<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "getgamesfromwishlist/{pagenumber}/{pagesize}/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<Page<Game>> getGamesFromWishlistPageable(@PathVariable int pagenumber, @PathVariable int pagesize, @PathVariable Long user_id) {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Page<Game> pagegames = service.getGamesFromWishlistPageable(pagenumber, pagesize,user_id);
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
	
	/**
	 * Metodo que trae un juego segun su nombre
	 * @param name el nombre del juego que queremos buscar
	 * @return en juego
	 */
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
	
	/**
	 * Metodo que trae una lista de juegos segun su publisher
	 * @param user_id es el id del publisher del que queremos saber sus juegos
	 * @return una lista de videojuegos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getgamesbypublisher/{user_id}")
	public ResponseEntity<List<Game>> getGamesByPublisher(@PathVariable Long user_id) {
		try {
			List<Game> gamelist = service.getGamesByPublisher(user_id);
			return new ResponseEntity<List<Game>>(gamelist, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que cuenta cuantos juegos ha vendido un usuario
	 * @param user_id es el id del usuario del que queremos saber cuantos juegos ha vendido
	 * @return es el id del usuario
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("countsellgames/{user_id}")
	public ResponseEntity<Long> countSellGames(@PathVariable Long user_id) {
		try {
			Long sell = service.countSellGames(user_id);
			return new ResponseEntity<Long>(sell, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que añade un juego a la libreria de un usuario
	 * @param game_id es el id del juego que vamos a añadir
	 * @param user_id es el id del usuario al que le vamos a añadir el juego
	 * @return un boolean
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("addgametolibrary/{game_id}/{user_id}")
	public ResponseEntity<Boolean> addGameToLibrary(@PathVariable Long game_id, @PathVariable 	Long user_id) {
		try {
			Boolean aux = service.addGameToLibrary(game_id,user_id);
			return new ResponseEntity<Boolean>(aux, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("addgametowishlist/{game_id}/{user_id}")
	public ResponseEntity<Boolean> addGameToWishlist(@PathVariable Long game_id, @PathVariable 	Long user_id) {
		try {
			Boolean aux = service.addGameToWishlist(game_id,user_id);
			return new ResponseEntity<Boolean>(aux, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que trae una lista de videojuegos que un usuario tiene comprados
	 * @param user_id es el id del usuario del que queremos saber su listado de juegos comprados
	 * @return una lista de juegos
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getlibrarybyid/{user_id}")
	public ResponseEntity<List<Game>> getLibraryById(@PathVariable Long user_id) {
		try {
			List<Game> gamelist = service.getLibraryById(user_id);
			return new ResponseEntity<List<Game>>(gamelist, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Game>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("isgameinlibrary/{user_id}/{game_id}")
	public ResponseEntity<Long> isGameInLibrary(@PathVariable Long user_id,@PathVariable Long game_id) {
		try {
			Long isgame = service.isGameInLibrary(user_id,game_id);
			return new ResponseEntity<Long>(isgame, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
