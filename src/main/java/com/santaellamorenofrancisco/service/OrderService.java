package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.model.Order;
import com.santaellamorenofrancisco.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository repository;
	
	public List<Order> getAllOrders() throws Exception{
		try {
			List<Order> orderlist = repository.findAll();
			return orderlist;
		} catch (Exception e) {
			throw new Exception("No ninguna orden en la base de datos");
		}
	}
	
	public Order getOrderById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<Order> orderlist = repository.findById(id);
				if (orderlist.isPresent()) {
					return orderlist.get();
				} else {
					throw new Exception("La orden no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Order createOrder(Order order) throws Exception, NullPointerException {	
		System.out.println(order);
		if (order != null && order.getId()==null) {
			try {
				System.out.println(order);
				return repository.save(order);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (order != null) {

			try {
				return updateOrder(order);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("La orden es nula");
		}
	}
	
	public Order updateOrder(Order order) throws Exception {
		if (order != null) {
			try {
				return repository.save(order);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("La orden es nula");
		}
	}
	
	public void deleteOrderById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<Order> orderlist;
			try {
				orderlist = Optional.ofNullable(getOrderById(id));
				if (!orderlist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("El la orden no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("La orden no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("La orden es nulo");
			} catch (Exception e) {
				throw new Exception("La orden no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	/**
	 * Metodo que trae una orden segun el id de un carro de la compra
	 * @param shoppingcart_id es el id del carro de la compra
	 * @return devuelve una lista de ordenes
	 * @throws Exception
	 */
	public List<Order> getOrderByShoppingCartId(Long shoppingcart_id) throws Exception{
		if(shoppingcart_id != null) {
			try {
				return repository.getOrderByShoppingCartId(shoppingcart_id);
			} catch (Exception e) {
				throw new Exception("Error al traer la lista de productos");
			}
		}else {
			throw new NullPointerException("El id es nulo");
		}
		
	}
	
	
}
