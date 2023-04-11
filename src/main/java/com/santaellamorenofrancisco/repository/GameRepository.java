package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Game;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

}
