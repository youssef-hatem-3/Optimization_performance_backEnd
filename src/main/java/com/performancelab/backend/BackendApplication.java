package com.performancelab.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Annotation says that this is Main application class and it applies component scanning, and auto-configuration.
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args); // ./mvnw spring-boot:run
	}

}
