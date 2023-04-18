package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Game;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {
	/**
	 * Consulta que trae la lista de juegos de forma paginada 
	 * @param var1 es la variable que contiene el numero de la pagina de la consulta que queremos traer y su limite
	 * por ejemplo funciona de tal manera que si pido la pagina 0 con limite 10 me traera los 10 primeros productos (del 1 al 10)
	 * pero si pido la pagina 1 con limite 10 me traera los productos (del 11 al 21)
	 * @return una pagina de productos
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM games g ORDER BY name asc")
	Page<Game> getGamesByPage(Pageable var1);
}
