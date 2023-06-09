package com.santaellamorenofrancisco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.ShoppingCart;
import com.santaellamorenofrancisco.service.ShoppingCartService;

@RestController
@RequestMapping("/shoppingcart")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ShoppingCartController {
	@Autowired
	ShoppingCartService service;
	
	/**
	 * Metodo que devuelve una lista con todos los shoppingcart
	 * @return devuelve una lista de los shoppingcart
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping
	public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
		try {
			List<ShoppingCart> shoppingcartlist = service.getAllShoppingCarts();
			return new ResponseEntity<List<ShoppingCart>>(shoppingcartlist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<ShoppingCart>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que devuelve un shoppingcart segun su id
	 * @param id es el id del shoppingcart
	 * @return un shoppingcart
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
		try {
			ShoppingCart shoppingcart = service.getShoppingCartById(id);
			return new ResponseEntity<ShoppingCart>(shoppingcart, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que borra un shoppingcart segun su id
	 * @param id es el id del shoppingcart que queremos borrar
	 * @return una respuesta
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("/{id}")
	public ResponseEntity<ShoppingCart> deleteShoppingCartById(@PathVariable Long id) {
		try {
			service.deleteShoppingCartById(id);
			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("getlastshoppingcartidnotpayedbyuserid/{user_id}")
	public ResponseEntity<Long> getLastShoppingCartIdNotPayedByUserId(@PathVariable Long user_id) {
		try {
			Long shoppingcartid = service.getLastShoppingCartIdNotPayedByUserId(user_id);
			return new ResponseEntity<Long>(shoppingcartid, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("isgameinshoppingcart/{user_id}/{game_id}")
	public ResponseEntity<Long> isGameInShoppingCart(@PathVariable Long user_id,@PathVariable Long game_id) {
		try {
			Long isgame = service.isGameInShoppingCart(user_id,game_id);
			return new ResponseEntity<Long>(isgame, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Long>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para calcular el precio total de los juegos que hay en el
	 * carro de la compra
	 * 
	 * @param shoppingcart_id es el id del carro del que vamos a calcular la suma de
	 *                        sus juegos
	 * @return un Double (el precio total)
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("totalprice/{shoppingcart_id}")
	public ResponseEntity<Double> getTotalPrice(@PathVariable Long shoppingcart_id) {
		try {
			Double totalprice = service.getTotalPrice(shoppingcart_id);
			return new ResponseEntity<Double>(totalprice, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Double>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que sirve para pagar un carro de la compra segun el saldo que tiene un cliente
	 * @param client_id es el id del cliente
	 * @param shoppingcart_id es el id del carro de la compra
	 * @return true si se ha realizado correctamente
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("payshoppingcart/{user_id}/{shoppingcart_id}")
	public ResponseEntity<Boolean> payShoppingCart(@PathVariable Long user_id, @PathVariable Long shoppingcart_id) {
		Boolean result = false;
		try {
			 result = service.payShoppingCart(user_id,shoppingcart_id);
			return new ResponseEntity<Boolean>(result, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Boolean>(result, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
