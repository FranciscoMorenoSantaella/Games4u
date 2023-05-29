package com.santaellamorenofrancisco.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santaellamorenofrancisco.model.Code;
import com.santaellamorenofrancisco.service.CodeService;
import com.santaellamorenofrancisco.service.GameService;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/code")
public class CodeController {
	
	@Autowired
	CodeService service;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path = "/generatecode")
	public ResponseEntity<Code> generateCode(@RequestParam BigDecimal balance) {
		try {
			Code code = service.generarCode(balance);
			return new ResponseEntity<Code>(code, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Code>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path = "/redeemcode")
	public ResponseEntity<Code> redeemCode(@RequestParam("user_id") Long user_id, @RequestParam("code") String code) {
	    try {
	        Code thecode = service.redeemCode(user_id, code);
	        return new ResponseEntity<>(thecode, new HttpHeaders(), HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	}

	
	
}
