package com.santaellamorenofrancisco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.ShoppingCart;
import com.santaellamorenofrancisco.service.ShoppingCartService;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService service;
	
	@GetMapping
	public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
		try {
			List<ShoppingCart> shoppingcartlist = service.getAllShoppingCarts();
			return new ResponseEntity<List<ShoppingCart>>(shoppingcartlist, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<List<ShoppingCart>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
		try {
			ShoppingCart shoppingcart = service.getShoppingCartById(id);
			return new ResponseEntity<ShoppingCart>(shoppingcart, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ShoppingCart> deleteShoppingCartById(@PathVariable Long id) {
		try {
			service.deleteShoppingCartById(id);
			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ShoppingCart>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
