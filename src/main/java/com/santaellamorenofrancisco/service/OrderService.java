package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
					//logger.error("The Client doesn't exists in the database.");
					throw new Exception("La orden no existe");
				}
			} catch (IllegalArgumentException e) {
				//logger.error("IllegalArgumentException in the method getClientById: " + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				//logger.error("Exception in the method getClientById: " + e);
				throw new Exception(e);
			}
		} else {
			//logger.error("NullPointerException in the method getClientById id equals to null.");
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public Order createOrder(Order user) throws Exception, NullPointerException {
		if (user != null && user.getId()==null) {
			try {
				return repository.save(user);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (user != null) {

			try {
				return updateOrder(user);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("La orden es nulo");
		}
	}
	
	public Order updateOrder(Order user) throws Exception {
		if (user != null) {
			try {
				return repository.save(user);
			} catch (Exception e) {
				//logger.error("Cannot update");
				throw new Exception(e);
			}
		} else {
			//logger.error("NullPointerException in the method updateClient client is null");
			throw new NullPointerException("La orden es nulo");
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
}
