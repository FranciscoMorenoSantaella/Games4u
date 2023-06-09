package com.santaellamorenofrancisco.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Game;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

	/**
	 * Consulta que trae la lista de juegos de forma paginada
	 * 
	 * @param var1 es la variable que contiene el numero de la pagina de la consulta
	 *             que queremos traer y su limite por ejemplo funciona de tal manera
	 *             que si pido la pagina 0 con limite 10 me traera los 10 primeros
	 *             productos (del 1 al 10) pero si pido la pagina 1 con limite 10 me
	 *             traera los productos (del 11 al 21)
	 * @return una pagina de productos
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM games g WHERE verified = true ORDER BY name asc")
	Page<Game> getGamesByPage(Pageable var1);

	/**
	 * Consulta que filtra los juegos segun un poco de su nombre para asi buscarlos
	 * 
	 * @param name es una parte del nombre de un videojuego o el nombre entero
	 * @return devuelve una lista de videojuegos
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM games WHERE games.name LIKE CONCAT ('%',:name,'%') AND verified = true LIMIT 4")
	List<Game> searchGameByName(@Param("name") String name);

	/**
	 * Metodo que busca un juego segun su nombre
	 * 
	 * @param name es el nombre del juego
	 * @return devuelve un videojuego
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM games WHERE name = ?1")
	Game getGameByName(@Param("name") String name);

	/**
	 * Metodo que devuelve un videojuego segun su publisher
	 * 
	 * @param user_id es el id del publisher
	 * @return devuelve una lista de los videojuegos que ha publicado un usuario en
	 *         especifico
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM games WHERE user_id = ?1")
	List<Game> getGamesByPublisher(@Param("user_id") Long user_id);

	/**
	 * Metodo para contar el numero de ventas de todos los videojuegos de un usuario
	 * 
	 * @param user_id es el id del usuario del que se quieren ver el numero de
	 *                ventas de sus juegos
	 * @return un entero que es el numero de ventas de sus juegos
	 */
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM games g, library l WHERE g.user_id = ?1 AND g.id = l.game_id AND l.user_id NOT IN (?1)")
	Long countSellGames(@Param("user_id") Long user_id);

	/**
	 * Metodo que añade un juego en la libreria de un usuario
	 * 
	 * @param game_id es el id del juego que vamos a añadir al usuario
	 * @param user_id es el id del usuario al que le vamos a añadir el juego
	 * @return devuelve un boolean para saber si la accion se ha realizado o no con
	 *         exito
	 */
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO library (game_id, user_id) VALUES (?1, ?2)")
	int addGameToLibrary(@Param("game_id") Long game_id, @Param("user_id") Long user_id);

	/**
	 * Metodo que añade un juego a la lista de deseados de un usuario
	 * 
	 * @param game_id es el id del juego que vamos a añadir al usuario
	 * @param user_id es el id del usuario al que le vamos a añadir el juego
	 * @return devuelve un boolean para saber si la accion se ha realizado o no con
	 *         exito
	 */
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO wishlists (game_id, user_id) VALUES (?1, ?2)")
	int addGameToWishlist(@Param("game_id") Long game_id, @Param("user_id") Long user_id);

	/**
	 * Consulta que trae los juegos de la libreria que tiene un usuario
	 * 
	 * @param user_id es id del usuario del que vamos a traer el listado
	 * @return un listado con los juegos
	 */
	@Query(nativeQuery = true, value = "SELECT g.* FROM games g, library l WHERE l.user_id = ?1 AND g.id = l.game_id;")
	List<Game> getLibraryById(@Param("user_id") Long user_id);

	/**
	 * Metodo que comprueba si un juego esta ya en la biblioteca del usuario
	 * 
	 * @param user_id es el id del usuario del que vamos a comprobar si tiene el
	 *                juego en la biblioteca
	 * @param game_id es el id del juego que queremos comprobar si esta en la
	 *                biblioteca
	 * @return devuelve el numero de filas, si devuelve una fila es que el juego
	 *         esta en la biblioteca, si devuelve 0 es que no esta en la biblioteca
	 */
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM games g, library l WHERE g.id = l.game_id AND l.user_id = ?1 AND g.id = ?2")
	Long isGameInLibrary(@Param("user_id") Long user_id, @Param("game_id") Long game_id);

	@Query(nativeQuery = true, value = "SELECT g.*, ur.valoracion as ratings FROM games g JOIN Library l ON g.id = l.game_id LEFT JOIN users_ratings ur ON ur.game_id = g.id AND ur.user_id = :user_id WHERE l.user_id = :user_id")
	Page<Game> getGameFromLibraryPageable(Pageable var1, @Param("user_id") Long user_id);
	
	@Query(nativeQuery = true, value="SELECT games.* FROM games, games_genres WHERE games.id = games_genres.game_id AND games_genres.genre_id =:genre_id AND verified = true")
	Page<Game> findByGenrePageable(@Param("genre_id") Long genre_id, Pageable var1);


	@Query(nativeQuery = true, value = "SELECT g.* FROM wishlists , games g WHERE wishlists.user_id = ?1 AND g.id = wishlists.game_id")
	Page<Game> getGamesFromWishlistPageable(Pageable var1, @Param("user_id") Long user_id);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) AS ventas, pay_date FROM shoppingcart sc, games g, orders_ o WHERE o.shoppingcart_id = sc.id AND g.id = ?1 AND o.game_id = g.id AND sc.ispayed = true GROUP BY pay_date")
	List<Object[]> getSalesByPayDate(@Param("game_id") Long game_id);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) AS ventas FROM shoppingcart sc JOIN orders_ o ON o.shoppingcart_id = sc.id JOIN games g ON o.game_id = g.id WHERE sc.ispayed = true AND g.id = ?1 AND sc.user_id <> g.user_id;")
	Long getSalesByGameId(@Param("game_id") Long game_id);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM library WHERE user_id = ?1")
	Long haveGamesInLibrary(@Param("user_id") Long user_id);

	@Query(nativeQuery = true, value = "SELECT * FROM games WHERE verified = false AND verified IS NOT NULL")
	Page<Game> getGamesNotVerified(Pageable var1);

	@Query(nativeQuery = true, value = "SELECT u.email FROM users u, games g WHERE g.user_id = u.id AND g.id = ?1")
	String getPublisherByGameId(@Param("game_id") Long game_id);
	
	@Query(nativeQuery = true, value = "SELECT u.id FROM users u, games g WHERE g.user_id = u.id AND g.id = ?1")
	Long getPublisherIdByGameId(@Param("game_id") Long game_id);

	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM wishlists WHERE game_id = ?1 AND user_id = ?2")
	int deleteFromWishList(@Param("game_id") Long game_id, @Param("user_id") Long user_id);

}
