package com.ecommerce2025.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mi API Ecommerce 2025 con el jdk 17")
                        .version("1.0")
                        .description("Documentación con Swagger y Springdoc"));
    }
}

