package com.santaellamorenofrancisco.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;

import com.santaellamorenofrancisco.model.FileMessage;
import com.santaellamorenofrancisco.model.Game;
import com.santaellamorenofrancisco.service.EmailSenderService;
import com.santaellamorenofrancisco.service.FileService;
import com.santaellamorenofrancisco.utils.FileUtils;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/email")
public class EmailSenderController {
	
	@Autowired
	EmailSenderService service;
	
	/**
	 * Metodo para enviar un email
	 * @param toEmail es al correo al que vamos a enviar el email
	 * @param subject es el asunto del email
	 * @param body es el cuerpo del email
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("sendemail")
	public ResponseEntity<Boolean> sendEmail(@RequestParam String toEmail,@RequestParam String subject,@RequestParam String body) {
		try {
			boolean aux = service.sendEmail(toEmail, subject, body);
			return new ResponseEntity<Boolean>(aux, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
