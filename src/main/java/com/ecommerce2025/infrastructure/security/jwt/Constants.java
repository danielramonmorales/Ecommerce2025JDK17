package com.ecommerce2025.infrastructure.security.jwt;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Clase que contiene constantes y utilidades relacionadas con JWT.
 * <p>
 * Class containing constants and utilities related to JWT.
 * </p>
 */
public class Constants {

    /**
     * Nombre del encabezado HTTP para autorización.
     * <p>
     * HTTP header name for authorization.
     * </p>
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * Prefijo del token JWT.
     * <p>
     * JWT token prefix.
     * </p>
     */
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    /**
     * Clave secreta utilizada para firmar los JWTs.
     * <p>
     * Secret key used to sign JWTs.
     * </p>
     */
    public static final String SUPER_SECRET_KEY = "nfC23HQY1MimASaEZeznZY+XxR1vlEciUAuj62QxYlutgQoe7Ef9y5gBe/Z5KZxICmZwxWNh7E5oSrFFPv7Jaw==";

    /**
     * Tiempo de expiración del token (en milisegundos).
     * <p>
     * Token expiration time (in milliseconds).
     * </p>
     */
    public static final long TOKEN_EXPIRATION_TIME = 1500000; // 25 minutos

    /**
     * Método utilitario para obtener la clave firmada en base a una clave secreta.
     * <p>
     * Utility method to get the signing key based on a secret key.
     * </p>
     *
     * @param secretKey clave secreta / secret key
     * @return Key objeto Key para firmar JWTs / Key object for signing JWTs
     */
    public static Key getSignedKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
