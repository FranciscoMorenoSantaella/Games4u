package com.santaellamorenofrancisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public Boolean sendEmail(String toEmail, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("games4uapp@gmail.com");
			message.setTo(toEmail);
			message.setText(body);
			message.setSubject(subject);
			mailSender.send(message);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
