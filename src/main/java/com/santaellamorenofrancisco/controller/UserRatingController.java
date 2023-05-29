package com.santaellamorenofrancisco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.model.UserRating;
import com.santaellamorenofrancisco.service.UserRatingService;


@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/userrating")
public class UserRatingController {
	@Autowired
	UserRatingService service;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping("setuserrating")
	public ResponseEntity<Integer> setUserRating(@RequestBody UserRating userrating) {
		try {
			System.out.println(userrating);
			Integer row = service.saveUserRating(userrating);
			return new ResponseEntity<Integer>(row, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
