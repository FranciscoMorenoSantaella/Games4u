package com.santaellamorenofrancisco.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Order;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Metodo que trae las ordenes segun el id del carrito de a compra
	 * @param shoppingcart_id es el id del carro de la compra del que queremos traer sus ordenes
	 * @return una lista de ordenes
	 */
	@Query(nativeQuery = true, value = "SELECT o.* FROM orders_ o WHERE o.shoppingcart_id = ?1")
	public List<Order> getOrderByShoppingCartId(@Param("shoppingcart_id") Long shoppingcart_id);
	
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM orders_ WHERE game_id = ?1 AND shoppingcart_id = ?2")
	public int deleteOrder(@Param("game_id") Long game_id, @Param("shoppingcart_id") Long shoppingcart_id);
	
}