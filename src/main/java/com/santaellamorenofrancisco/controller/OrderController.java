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

import com.santaellamorenofrancisco.model.Order;
import com.santaellamorenofrancisco.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService service;
	
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		try {
			List<Order> orderlist = service.getAllOrders();
			return new ResponseEntity<List<Order>>(orderlist, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Order>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		try {
			Order order = service.getOrderById(id);
			return new ResponseEntity<Order>(order, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<Order>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
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
