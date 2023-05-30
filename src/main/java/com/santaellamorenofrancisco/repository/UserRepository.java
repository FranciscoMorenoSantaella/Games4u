package com.santaellamorenofrancisco.repository;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE users SET balance = balance + ?1 WHERE id = ?2")
	public int addBalance(@Param("balance") BigDecimal balance,@Param("user_id") Long user_id);

}
