package com.ecommerce2025.infrastructure.security.jwt;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Clase de constantes utilizadas en la generación y validación de tokens JWT.
 * Contiene configuraciones generales como el header HTTP, el prefijo del token,
 * la clave secreta y el tiempo de expiración.
 */
public class Constants {

    /**
     * Nombre del header HTTP donde se debe enviar el token JWT.
     * Ejemplo: Authorization: Bearer <token>
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * Prefijo usado para identificar que el token es del tipo Bearer (portador).
     * Se concatena antes del token en el header de autorización.
     */
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    /**
     * Clave secreta usada para firmar los tokens JWT.
     * ⚠ Importante: en un entorno productivo, esta clave debería almacenarse
     * en una variable de entorno o en un archivo de configuración seguro.
     */
    public static final String SUPER_SECRET_KEY = "f68a41e098cf7ecb8924645bfe335941beb068e7bebaf0bba26549c0693560df08cace69debbc59f6d2e30a328570dc331c1ec2f998a43cd0340b08065d4318a";

    /**
     * Tiempo de expiración del token en milisegundos.
     * En este caso: 1,500,000 ms = 25 minutos.
     */
    public static final long TOKEN_EXPIRATION_TIME = 1500000; // esto hace lo que dure mi token

    /**
     * Método auxiliar para obtener una clave criptográfica HMAC firmada,
     * a partir de una cadena de texto.
     *
     * @param secretKey clave secreta en texto plano
     * @return clave lista para firmar tokens JWT
     */
    public static Key getSignedKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
