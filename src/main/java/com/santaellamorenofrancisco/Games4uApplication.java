package com.santaellamorenofrancisco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.santaellamorenofrancisco.service.EmailSenderService;

@SpringBootApplication
@EntityScan("com.santaellamorenofrancisco.model")
public class Games4uApplication {
	@Autowired
	private EmailSenderService senderService;
	public static void main(String[] args) {
		SpringApplication.run(Games4uApplication.class, args);
	}
}
