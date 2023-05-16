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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@GetMapping(path = "/generatecode/{balance}")
	public ResponseEntity<Code> generateCode(@PathVariable BigDecimal balance) {
		try {
			Code code = service.generarCode(balance);
			return new ResponseEntity<Code>(code, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Code>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(path = "/redeemcode/{user_id}/{code}")
	public ResponseEntity<Code> redeemCode(@PathVariable Long user_id, @PathVariable String code) {
		try {
			Code thecode = service.redeemCode(user_id,code);
			return new ResponseEntity<Code>(thecode, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Code>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
