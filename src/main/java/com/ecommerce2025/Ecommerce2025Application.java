package com.ecommerce2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce2025.infrastructure.adapter.implJpa")
public class Ecommerce2025Application {

	public static void main(String[] args) {
		SpringApplication.run(Ecommerce2025Application.class, args);
	}

}
