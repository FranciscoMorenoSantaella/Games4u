package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.Genre;
import com.santaellamorenofrancisco.repository.GenreRepository;

@Service
public class GenreService {
	@Autowired
	GenreRepository repository;
	
	
	public List<Genre> getAllGenres() throws Exception{
		try {
			List<Genre> genrelist = repository.findAll();
			return genrelist;
		} catch (Exception e) {
			throw new Exception("No hay generos en la base de datos");
		}
	}
	
	public Genre getGenreById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<Genre> genrelist = repository.findById(id);
				if (genrelist.isPresent()) {
					return genrelist.get();
				} else {
					//logger.error("The Client doesn't exists in the database.");
					throw new Exception("El genero no existe");
				}
			} catch (IllegalArgumentException e) {
				//logger.error("IllegalArgumentException in the method getClientById: " + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				//logger.error("Exception in the method getClientById: " + e);
				throw new Exception(e);
			}
		} else {
			//logger.error("NullPointerException in the method getClientById id equals to null.");
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Genre createGenre(Genre genre) throws Exception, NullPointerException {
		if (genre != null && genre.getId()==null) {
			try {
				return repository.save(genre);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (genre != null) {

			try {
				return updateGenre(genre);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("El genero es nulo");
		}
	}
	
	public Genre updateGenre(Genre genre) throws Exception {
		if (genre != null) {
			try {
				return repository.save(genre);
			} catch (Exception e) {
				//logger.error("Cannot update");
				throw new Exception(e);
			}
		} else {
			//logger.error("NullPointerException in the method updateClient client is null");
			throw new NullPointerException("El genero es nulo");
		}
	}
	
	public void deleteGenreById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<Genre> genrelist;
			try {
				genrelist = Optional.ofNullable(getGenreById(id));
				if (!genrelist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("El genero no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("El genero no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("El genero es nulo");
			} catch (Exception e) {
				throw new Exception("El genero no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
}
