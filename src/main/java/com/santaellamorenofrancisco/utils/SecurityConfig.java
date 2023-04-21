package com.santaellamorenofrancisco.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.santaellamorenofrancisco.service.UserService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserService userservice;
	 	@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
