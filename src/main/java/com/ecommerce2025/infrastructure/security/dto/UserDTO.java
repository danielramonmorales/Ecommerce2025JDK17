package com.ecommerce2025.infrastructure.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO que representa las credenciales del usuario para el inicio de sesión.
 *
 * @param username Nombre de usuario o correo electrónico
 * @param password Contraseña del usuario
 */
public record UserDTO(

        @Schema(description = "Nombre de usuario o email", example = "usuario@correo.com")
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @Schema(description = "Contraseña del usuario", example = "1234password")
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password

) {}
