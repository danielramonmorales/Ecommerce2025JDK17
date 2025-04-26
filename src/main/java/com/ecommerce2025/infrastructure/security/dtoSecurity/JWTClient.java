package com.ecommerce2025.infrastructure.security.dtoSecurity;

/**
 * DTO para enviar el token JWT al cliente después de un inicio de sesión exitoso.
 * <p>
 * DTO to send the JWT token to the client after a successful login.
 * </p>
 *
 * @param token el token JWT generado / the generated JWT token
 */
public record JWTClient(String token) {
}
