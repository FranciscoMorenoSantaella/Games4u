package com.santaellamorenofrancisco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	/**
	 * Metodo que devuelve una lista de todos los usuarios
	 * @return una lista de todos los usuarios
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> userlist = service.getAllUsers();
			return new ResponseEntity<List<User>>(userlist, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<User>>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que devuelve un usuario segun su id
	 * @param id es el id del usuario que buscamos
	 * @return un usuario
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		try {
			User user = service.getUserById(id);
			return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Borra un usuario segun su id
	 * @param id es el id del usuario que vamos a borrar
	 * @return un usuario
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUserByid(@PathVariable Long id) {
		try {
			service.deleteUserById(id);
			return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
