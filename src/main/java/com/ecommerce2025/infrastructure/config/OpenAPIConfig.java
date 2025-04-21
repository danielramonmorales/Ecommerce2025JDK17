package com.ecommerce2025.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para la documentación de la API utilizando OpenAPI (Swagger)
 * con la librería Springdoc. Expone un bean de tipo OpenAPI que contiene la
 * información general de la API.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Crea y configura una instancia de OpenAPI con información personalizada
     * como título, versión y descripción. Esta configuración será usada para
     * generar automáticamente la documentación de la API.
     *
     * @return instancia configurada de OpenAPI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mi API Ecommerce 2025 con el JDK 17") // Título que se mostrará en la documentación.
                        .version("1.0") // Versión de la API.
                        .description("Documentación con Swagger y Springdoc")); // Breve descripción.
    }
}
