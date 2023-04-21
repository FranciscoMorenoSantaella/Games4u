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
	
	public List<Game> searchGameByName(String name) throws Exception{
		try {
			List<Game> gamelist = repository.searchGameByName(name);
			return gamelist;
		} catch (Exception e) {
			throw new Exception("No hay juegos en la base de datos");
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


}
