package com.santaellamorenofrancisco.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	/**
	 * Consulta que calcula el precio total (la suma) de todos los productos que hay
	 * en un carro de la compra
	 * 
	 * @param shoppingcart_id es el id del carro de la compra del que vamos a
	 *                        calcular su precio total
	 * @return devuelve una Double
	 */
	@Query(nativeQuery = true, value = "SELECT SUM(g.precio) AS PRICE FROM orders_ o, shoppingcart sc, games g WHERE o.shoppingcart_id = sc.id AND g.id = o.game_id AND sc.id = ?1")
	public Double getTotalPrice(@Param("shoppingcart_id") Long shoppingcart_id);
	
	/**
	 * Metodo que resta el precio total de producto del carro de la compra con el
	 * saldo del usuario (esta consulta sirve para pagar el carro de la compra)
	 * 
	 * @param user_id       es el id del usuario que va a pagar el carro de la
	 *                        compra
	 * @param shoppingcart_id es el id del carro de la compra que que el cliente va
	 *                        a pagar
	 * @return el resultado de la resta del precio total y el saldo, este resultado
	 *         sera el saldo actual del cliente
	 */
	@Query(nativeQuery = true, value = "SELECT (u.balance) - SUM(g.precio) FROM games g, orders_ o,  shoppingcart sc, users u WHERE g.id = o.game_id AND sc.id = o.shoppingcart_id AND sc.id = ?1 AND sc.ispayed = false AND u.id = sc.user_id GROUP BY u.id")
	public Double payShoppingCart(@Param("shoppingcart_id") Long shoppingcart_id);
	
	
}
