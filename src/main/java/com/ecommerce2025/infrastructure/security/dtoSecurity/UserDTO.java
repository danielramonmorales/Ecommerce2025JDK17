package com.ecommerce2025.infrastructure.security.dtoSecurity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para recibir las credenciales del usuario en el proceso de autenticación.
 * <p>
 * DTO to receive user credentials during the authentication process.
 * </p>
 */
public record UserDTO(
        @Schema(description = "Nombre de usuario / Username")
        String username,

        @Schema(description = "Contraseña del usuario / User password")
        String password
) {
}
