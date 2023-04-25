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

import com.santaellamorenofrancisco.model.Order;
import com.santaellamorenofrancisco.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
public class OrderController {
	@Autowired
	OrderService service;
	
	/**
	 * Metodo que devuelve una lista con todas las ordenes
	 * @return una lista de ordenes
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		try {
			List<Order> orderlist = service.getAllOrders();
			return new ResponseEntity<List<Order>>(orderlist, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Order>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que trae una orden segun su id
	 * @param id es el id de la orden que queremos buscar
	 * @return una orden
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		try {
			Order order = service.getOrderById(id);
			return new ResponseEntity<Order>(order, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que borra una orden segun su id
	 * @param id es el id de la orden que queremos buscar
	 * @return la respuesta
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> deleteOrderByid(@PathVariable Long id) {
		try {
			service.deleteOrderById(id);
			return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
