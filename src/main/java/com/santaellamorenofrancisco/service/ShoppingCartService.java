package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.ShoppingCart;
import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.repository.ShoppingCartRepository;
import com.santaellamorenofrancisco.repository.UserRepository;

@Service
public class ShoppingCartService {
	@Autowired
	ShoppingCartRepository repository;
	
	@Autowired 
	UserRepository userrepository;
	
	
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
	
	public Long getLastShoppingCartIdNotPayedByUserId(Long user_id)
			throws Exception, IllegalArgumentException, NullPointerException {
		if (user_id != null) {
			try {
				Long shoppingcartid = repository.getLastShoppingCartIdNotPayedByUserId(user_id);
				if (shoppingcartid == null) {
					User u = userrepository.findById(user_id).get();
					ShoppingCart sc = new ShoppingCart();
					sc.setUser(u);
					sc.setIspayed(false);
					sc = createShoppingCart(sc);
					repository.save(sc);
					return sc.getId();
				} else {
					return shoppingcartid;
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
	}
	
	public Long isGameInShoppingCart (Long user_id, Long game_id) throws Exception, IllegalArgumentException, NullPointerException {
		if (user_id != null && game_id != null) {
			try {
				Long isgame = repository.isGameInShoppingCart(user_id,game_id);
				if (isgame != null) {
					return isgame;
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
	
	/**
	 * Metodo que calcula el precio total de los juegos que hay en un carro de la
	 * compra
	 * 
	 * @param shoppingcart_id es el id del carro de la compra del que queremos saber
	 *                        el precio total de sus juegos
	 * @return devuelve un Double que es el precio total de los juegos
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public Double getTotalPrice(Long shoppingcart_id) throws Exception, IllegalArgumentException, NullPointerException {
		if (shoppingcart_id != null) {
			try {
				Double totalprice = repository.getTotalPrice(shoppingcart_id);
				return totalprice;
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	/**
	 * Metodo que sirve para comprar un carro de la compra se resta el saldo del
	 * cliente segun el precio total del carro y se pone el carro en ispayed = true
	 * 
	 * @param user_id       es el id del cliente que va a pagar el carro de la
	 *                        compra
	 * @param shoppingcart_id es el id del carro que vamos a compra
	 * @return un booleano (true si se ha podido comprar o false si no)
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public Boolean payShoppingCart(Long user_id, Long shoppingcart_id)
			throws Exception, IllegalArgumentException, NullPointerException {
		Boolean result = false;
		if (user_id != null && shoppingcart_id != null) {
			try {
				Double userbalance = repository.payShoppingCart(shoppingcart_id);
				ShoppingCart sc = getShoppingCartById(shoppingcart_id);
				User u = userrepository.findById(user_id).get();
				if (userbalance >= 0) {
					u.setBalance(userbalance);
					userrepository.save(u);
					updateShoppingCart(sc);
					sc.setTotalprice(getTotalPrice(shoppingcart_id));
					sc.setIspayed(true);
					repository.save(sc);
					result = true;
					return result;
				} else {
					return result;
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
	
}
