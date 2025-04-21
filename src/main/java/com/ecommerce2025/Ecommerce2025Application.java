package com.ecommerce2025;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ecommerce2025Application {

	public static void main(String[] args) {
		// Cargar variables del .env
		Dotenv dotenv = Dotenv.load();

		// Asignar como propiedades del sistema para que Spring las pueda usar
		System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
		System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
		System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));

		// Arrancar la aplicación
		SpringApplication.run(Ecommerce2025Application.class, args);
	}
}
