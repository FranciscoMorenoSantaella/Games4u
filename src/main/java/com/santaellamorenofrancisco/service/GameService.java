package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	GameRepository repository;
	
	public List<Game> getAllGames() throws Exception{
		try {
			List<Game> gamelist = repository.findAll();
			return gamelist;
		} catch (Exception e) {
			throw new Exception("No hay juegos en la base de datos");
		}
	}
	
	public Game getGameById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<Game> gamelist = repository.findById(id);
				if (gamelist.isPresent()) {
					return gamelist.get();
				} else {
					throw new Exception("El juego no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Long getSalesByGameId(Long game_id) throws Exception, IllegalArgumentException, NullPointerException {
		if (game_id != null) {
			try {
				Long sells = repository.getSalesByGameId(game_id);
				return sells;
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	
	public Long isGameInLibrary(Long user_id, Long game_id) throws Exception, IllegalArgumentException, NullPointerException {
		if (user_id != null && game_id !=null) {
			try {
				Long isgame = repository.isGameInLibrary(user_id,game_id);
				if (isgame != null) {
					return isgame;
				} else {
					throw new Exception("El juego no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Long setGameVerified(Long game_id) throws Exception {
		if (game_id != null) {
			try {
				System.out.println(game_id);
				return repository.setGameVerified(game_id);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El usuario es nulo");
		}
	}
	
	public List<Game> getGamesByPublisher(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				List<Game> gamelist = repository.getGamesByPublisher(id);
				if (gamelist != null) {
					return gamelist;
				} else {
					throw new Exception("No ha publicado juegos");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public List<Object[]> getSalesByPayDate(Long game_id) throws Exception, IllegalArgumentException, NullPointerException {
		if (game_id != null) {
			try {
				List<Object[]> sells = repository.getSalesByPayDate(game_id);
				if (sells != null) {
					return sells;
				} else {
					throw new Exception("No hay ventas");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Game createGame(Game game) throws Exception, NullPointerException {
		if (game != null && game.getId()==null) {
			try {
				return repository.save(game);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (game != null) {
			try {
				return updateGame(game);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("El juego es nulo");
		}
	}
	
	public Game updateGame(Game game) throws Exception {
		if (game != null) {
			try {
				return repository.save(game);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El juego es nulo");
		}
	}
	
	public void deleteGameById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<Game> gamelist;
			try {
				gamelist = Optional.ofNullable(getGameById(id));
				if (!gamelist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("El juego no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("El juego no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("El juego es nulo");
			} catch (Exception e) {
				throw new Exception("El juego no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Page<Game> getGameByPage(int pagenumber, int pagesize) throws Exception {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Sort sort = Sort.by(Sort.Direction.ASC, "id");
				Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
				return repository.getGamesByPage(pageable);
			} catch (Exception e) {
				throw new Exception("Error en la consulta", e);
			}
		} else {
			throw new Exception("El numero de pagina y/o el limite no puede ser menor que 0");
		}
	}
	
	public Page<Game> getGameFromLibraryPageable(int pagenumber, int pagesize,Long user_id) throws Exception {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Pageable pageable = PageRequest.of(pagenumber, pagesize);
				return repository.getGameFromLibraryPageable(pageable,user_id);
			} catch (Exception e) {
				throw new Exception("Error en la consulta", e);
			}
		} else {
			throw new Exception("El numero de pagina y/o el limite no puede ser menor que 0");
		}
	}
	
	public Page<Game> getGamesFromWishlistPageable(int pagenumber, int pagesize,Long user_id) throws Exception {
		if (pagenumber >= 0 && pagesize >= 0) {
			try {
				Pageable pageable = PageRequest.of(pagenumber, pagesize);
				return repository.getGamesFromWishlistPageable(pageable,user_id);
			} catch (Exception e) {
				throw new Exception("Error en la consulta", e);
			}
		} else {
			throw new Exception("El numero de pagina y/o el limite no puede ser menor que 0");
		}
	}
	
	public List<Game> searchGameByName(String name) throws Exception{
		try {
			List<Game> gamelist = repository.searchGameByName(name);
			return gamelist;
		} catch (Exception e) {
			throw new Exception("No hay juegos en la base de datos");
		}
	}
	
	public Long countSellGames(Long user_id) throws Exception{
		try {
			Long sell = repository.countSellGames(user_id);
			return sell;
		} catch (Exception e) {
			throw new Exception("No se ha podido realizar la consulta");
		}
	}
	
	public Boolean addGameToLibrary(Long game_id, Long user_id) throws Exception{
		try {
		    repository.addGameToLibrary(game_id,user_id);
			return true;
		} catch (Exception e) {
			throw new Exception("No se ha podido realizar la consulta");
		}
	}
	
	public Boolean addGameToWishlist(Long game_id, Long user_id) throws Exception{
		try {
		    repository.addGameToWishlist(game_id,user_id);
			return true;
		} catch (Exception e) {
			throw new Exception("No se ha podido realizar la consulta");
		}
	}
	
	public Game getGameByName(String name) throws Exception{
		try {
			Game game = repository.getGameByName(name);
			return game;
		} catch (Exception e) {
			throw new Exception("No se ha encontrado el juego en la base de datos");
		}
	}
	
	public List<Game> getLibraryById(Long user_id) throws NullPointerException, IllegalArgumentException, Exception {
		if (user_id != null) {
			try {
				List <Game>gamelist = repository.getLibraryById(user_id);
				if (gamelist != null) {
					return gamelist;
				} else {
					throw new Exception("No tienes juegos en la libreria");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("No tienes juegos en la libreria");
			} catch (NullPointerException e1) {
				throw new NullPointerException("La lista es nula");
			} catch (Exception e) {
				throw new Exception("La lista no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	



}
