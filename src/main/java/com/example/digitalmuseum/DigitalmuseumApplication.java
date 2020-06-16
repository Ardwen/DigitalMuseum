package com.example.digitalmuseum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.digitalmuseum.dao")
@SpringBootApplication
public class DigitalmuseumApplication {

	public static void main(String[] args) {

		SpringApplication.run(DigitalmuseumApplication.class, args);
	}

}
