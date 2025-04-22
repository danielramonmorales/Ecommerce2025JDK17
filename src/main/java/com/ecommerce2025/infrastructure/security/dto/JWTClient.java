package com.ecommerce2025.infrastructure.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con token JWT y datos del usuario")
public record JWTClient(
        @Schema(description = "ID del usuario", example = "1")
        Integer id,

        @Schema(description = "Token JWT generado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "Tipo de usuario", example = "CLIENTE")
        String type
) {}

