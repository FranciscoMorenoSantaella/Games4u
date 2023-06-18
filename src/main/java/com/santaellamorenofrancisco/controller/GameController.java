package com.santaellamorenofrancisco.controller;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.santaellamorenofrancisco.model.File;
import com.santaellamorenofrancisco.model.FileMessage;
import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.model.Genre;
import com.santaellamorenofrancisco.model.Platform;
import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.repository.FileRepository;
import com.santaellamorenofrancisco.repository.GameRepository;
import com.santaellamorenofrancisco.repository.GenreRepository;
import com.santaellamorenofrancisco.repository.PlatformRepository;
import com.santaellamorenofrancisco.repository.UserRepository;
import com.santaellamorenofrancisco.service.GameService;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
public class GameController {
	@Autowired
	GameService service;
	@Autowired
	GameRepository gamerepository;
	@Autowired
	GenreRepository genrerepository;
	@Autowired
	FileRepository filerepository;
	@Autowired
	PlatformRepository platformrepository;
	@Autowired
	UserRepository userrepository;
	@Autowired
	GoogleDriveController googledrivecontroller;
	
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
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getpublisherbygameid/{game_id}")
	public ResponseEntity<String> getPublisherByGameId(@PathVariable Long game_id) {
		try {
			String email = service.getPublisherByGameId(game_id);
			return new ResponseEntity<String>(email, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("havegamesinlibrary/{user_id}")
	public ResponseEntity<Long> haveGamesInLibrary(@PathVariable Long user_id) {
		try {
			Long count = service.haveGamesInLibrary(user_id);
			return new ResponseEntity<Long>(count, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getsalesbygameid/{game_id}")
	public ResponseEntity<Long> getSalesByGameId(@PathVariable Long game_id) {
		try {
			Long sells = service.getSalesByGameId(game_id);
			return new ResponseEntity<Long>(sells, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getpublisheridbygameid/{game_id}")
	public ResponseEntity<Long> getPublisherIdByGameId(@PathVariable Long game_id) {
		try {
			Long publisher = service.getPublisherIdByGameId(game_id);
			return new ResponseEntity<Long>(publisher, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getsalesbypaydate/{game_id}")
	public ResponseEntity<List<Object[]>> getSalesByPayDate(@PathVariable Long game_id) {
		try {
			List<Object[]> sells = service.getSalesByPayDate(game_id);
			return new ResponseEntity<List<Object[]>>(sells, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<List<Object[]>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
	
	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("deletefromwishlist/{game_id}/{user_id}")
	public ResponseEntity<Integer> deleteGameFromWishList(@PathVariable Long game_id, @PathVariable Long user_id) {
		try {
			int rows = service.deleteGameFromWishlist(game_id,user_id);
			return new ResponseEntity<Integer>(rows,new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
	@RequestMapping(value = "getgamesnotverified/{pagenumber}/{pagesize}", method = RequestMethod.GET)
	public ResponseEntity<Page<Game>> getGamesNotVerifiedPageable(@PathVariable int pagenumber, @PathVariable int pagesize) {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Page<Game> pagegames = service.getGamesNotVerifiedPageable(pagenumber, pagesize);
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
	@RequestMapping(value = "findbygenre/{pagenumber}/{pagesize}/{genre_id}", method = RequestMethod.GET)
	public ResponseEntity<Page<Game>> findByGenrePageable(@PathVariable int pagenumber, @PathVariable int pagesize, @PathVariable Long genre_id) {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Page<Game> pagegames = service.findByGenrePageable(pagenumber, pagesize,genre_id);
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
	@PutMapping("setgameverified/{game_id}")
	public ResponseEntity<Boolean> setGameVerified(@PathVariable Long game_id) {
		try {
			Boolean aux = service.setGameVerified(game_id);
			return new ResponseEntity<Boolean>(aux, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping("setgameverifiednull/{game_id}")
	public ResponseEntity<Boolean> setGameVerifiedNull(@PathVariable Long game_id) {
		try {
			Boolean aux = service.setGameVerfiedNull(game_id);
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
		
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value ="creategame")
	public ResponseEntity<Game> createGame(@RequestParam("name") String name,
	                                       @RequestParam("description") String description,
	                                       @RequestParam("price") Float price,
	                                       @RequestParam("genre") List<Long> genre_id,
	                                       @RequestParam("platform") List<Long> platform_id,
	                                       @RequestParam("user_id") Long user_id,
	                                       @RequestParam("executable") MultipartFile executable,
	                                       @RequestParam("image") MultipartFile image) throws IOException {
		List<Genre> genre = genrerepository.findAllById(genre_id);
	    List<Platform> platformlist = platformrepository.findAllById(platform_id);
	    Set<Genre> genreslist = new HashSet<>(genre);
	    Set<Platform> platforms = new HashSet<>(platformlist);
	    Optional<User> useroptional = userrepository.findById(user_id);
	    User user = useroptional.orElse(null);
	    if (user == null) {
	        return ResponseEntity.badRequest().build();
	    }



	    Game game = new Game();
	    game.setname(name);
	    game.setDescription(description);
	    game.setPrecio(price);
	    game.setGenreslist(genreslist);
	    game.setPublisher(user);
	    // game.setFiles();
	    game.setPlatforms(platforms);
	    System.out.println(game.getGenreslist());
	    
	    Game savedgame = gamerepository.save(game);
	    googledrivecontroller.uploadFile(executable, savedgame.getId(), true);
	    googledrivecontroller.uploadFile(image, savedgame.getId(), false);
	    return ResponseEntity.ok(savedgame);
	}

	
	
	
}
