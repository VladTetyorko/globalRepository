package com.census;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration
public class CensusApplication {

	private static final Logger logger = LoggerFactory.getLogger(CensusApplication.class.getName());

	public static void main(String[] args) {
		logger.info("Application started");
		SpringApplication.run(CensusApplication.class, args);
		logger.info("Application finished");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		if (logger.isDebugEnabled())
			logger.debug("Bcrypt encoder created");
		return new BCryptPasswordEncoder();
	}

}
