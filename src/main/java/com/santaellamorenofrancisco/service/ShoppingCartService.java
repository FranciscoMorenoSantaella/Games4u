package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.ShoppingCart;
import com.santaellamorenofrancisco.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
	@Autowired
	ShoppingCartRepository repository;
	
	
	public List<ShoppingCart> getAllShoppingCarts() throws Exception{
		try {
			List<ShoppingCart> shoppingcartlist = repository.findAll();
			return shoppingcartlist;
		} catch (Exception e) {
			throw new Exception("No hay carro de la compras en la base de datos");
		}
	}
	
	public ShoppingCart getShoppingCartById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<ShoppingCart> shoppingcartlist = repository.findById(id);
				if (shoppingcartlist.isPresent()) {
					return shoppingcartlist.get();
				} else {
					throw new Exception("El carro de la compra no existe");
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
	
	public ShoppingCart createShoppingCart(ShoppingCart shoppingcart) throws Exception, NullPointerException {
		if (shoppingcart != null && shoppingcart.getId()==null) {
			try {
				return repository.save(shoppingcart);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (shoppingcart != null) {

			try {
				return updateShoppingCart(shoppingcart);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("El carro de la compra es nulo");
		}
	}
	
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingcart) throws Exception {
		if (shoppingcart != null) {
			try {
				return repository.save(shoppingcart);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El carro de la compra es nulo");
		}
	}
	
	public void deleteShoppingCartById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<ShoppingCart> shoppingcartlist;
			try {
				shoppingcartlist = Optional.ofNullable(getShoppingCartById(id));
				if (!shoppingcartlist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("El carro de la compra no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("El carro de la compra no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("El carro de la compra es nulo");
			} catch (Exception e) {
				throw new Exception("El carro de la compra no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
}
