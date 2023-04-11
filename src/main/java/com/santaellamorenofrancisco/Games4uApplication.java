package com.santaellamorenofrancisco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.santaellamorenofrancisco.model")
public class Games4uApplication {
	public static void main(String[] args) {
		SpringApplication.run(Games4uApplication.class, args);
	}
}
