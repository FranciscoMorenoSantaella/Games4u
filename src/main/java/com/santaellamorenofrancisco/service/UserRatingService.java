package com.santaellamorenofrancisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.model.UserRating;
import com.santaellamorenofrancisco.repository.UserRatingRepository;
import com.santaellamorenofrancisco.repository.UserRepository;

@Service
public class UserRatingService {
	@Autowired
	UserRatingRepository repository;
	
	public Integer saveUserRating(UserRating userrating) throws Exception {
	    if (userrating != null) {
	        try {
	            // Verificar si ya existe un UserRating para el juego y el usuario correspondientes
	            UserRating existingRating = repository.findByGameAndUser(userrating.getGame(), userrating.getUser());

	            if (existingRating != null) {
	                // Si ya existe, actualizar la valoración utilizando el método setUserRating
	                repository.setUserRating(userrating.getValoracion(), userrating.getGame().getId(), userrating.getUser().getId());
	                return  repository.setUserRating(userrating.getValoracion(), userrating.getGame().getId(), userrating.getUser().getId());
	            } else {
	                // Si no existe, guardar un nuevo UserRating utilizando el método repository.save
	                UserRating ur = repository.save(userrating);
	                if(ur != null) {
	                	return 1;
	                }else {
	                	return 0;
	                }
	            }
	        } catch (Exception e) {
	            throw new Exception(e);
	        }
	    } else {
	        throw new NullPointerException("El juego es nulo");
	    }
	}

}
