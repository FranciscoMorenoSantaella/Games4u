package com.santaellamorenofrancisco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
	 * Metodo que sirve para crear un usuario encriptando su contraseña
	 * @param user es el usuario que se va a registrar
	 * @return el usuario que se ha creado
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if (user != null) {
			try {
				String encodedPassword = passwordEncoder.encode(user.getPassword());
			    user.setPassword(encodedPassword);
				User createUser = service.createUser(user);
				return new ResponseEntity<User>(createUser, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST,path = "/{login}")
	public ResponseEntity<User> login(@RequestBody User user) {
		if (user != null) {
			try {
			    String encriptedPasswd=passwordEncoder.encode(user.getPassword());
			    System.out.println(encriptedPasswd);
				return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<User>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
