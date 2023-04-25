package com.santaellamorenofrancisco.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.model.User;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE uid = ?1")
	Optional<User> getUserByUid(@Param("uid") String uid);

}
