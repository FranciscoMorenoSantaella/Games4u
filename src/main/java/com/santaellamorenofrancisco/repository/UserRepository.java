package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
}
