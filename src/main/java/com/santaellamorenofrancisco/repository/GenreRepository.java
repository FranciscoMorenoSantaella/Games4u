package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Genre;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
