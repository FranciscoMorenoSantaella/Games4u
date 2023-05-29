package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.model.UserRating;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE users_ratings SET valoracion = :rating WHERE game_id = :game_id AND user_id = :user_id")
	public Integer setUserRating(@Param("rating") int rating, @Param("game_id") Long game_id, @Param("user_id") Long user_id);

	UserRating findByGameAndUser(Game game, User user);
	
	@Query(nativeQuery = true, value = "SELECT AVG(valoracion) FROM users_ratings WHERE game_id = ?1")
	public Integer selectAvgRatingByGameId(@Param("game_id") Long game_id);
	
}
