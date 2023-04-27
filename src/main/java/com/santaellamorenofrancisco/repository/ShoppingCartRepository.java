package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.ShoppingCart;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	
	/**
	 * Metodo que trae el ultimo carro de la compra sin pagar que tiene un usuario
	 * @param user_id es el id del usuario del que queremos saber el carro de la compra
	 * @return el id del carro de la compra
	 */
	@Query(nativeQuery = true, value = "SELECT sc.id FROM shoppingcart sc, users u WHERE u.id = sc.user_id AND sc.ispayed = false AND u.id = ?1 ORDER BY sc.id DESC LIMIT 1")
	public Long getLastShoppingCartIdNotPayedByUserId(@Param("user_id") Long user_id);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM orders_ o, shoppingcart sc WHERE sc.user_id = ?1 AND o.game_id = ?2 AND sc.id = o.shoppingcart_id")
	public Long isGameInShoppingCart(@Param("user_id") Long user_id, @Param("game_id") Long game_id);
}
