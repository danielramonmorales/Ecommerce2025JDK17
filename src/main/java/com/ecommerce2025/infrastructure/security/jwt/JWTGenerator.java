package com.ecommerce2025.infrastructure.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ecommerce2025.infrastructure.security.jwt.Constants.*;

/**
 * Generador de tokens JWT para autenticación de usuarios.
 * <p>
 * JWT Token generator for user authentication.
 * </p>
 */
@Service
public class JWTGenerator {

    /**
     * Método para generar un token JWT.
     * <p>
     * Method to generate a JWT token.
     * </p>
     *
     * @param username nombre de usuario para incluir en el token / username to include in the token
     * @return token JWT completo con "Bearer " / complete JWT token with "Bearer "
     */
    public String getToken(String username) {
        // Obtiene las autoridades del contexto de seguridad
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().toString()
        );

        // Construcción del JWT
        String token = Jwts.builder()
                .setId("ecommerce") // ID del token
                .setSubject(username) // Usuario al que pertenece el token
                .claim("authorities", authorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) // Roles del usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)) // Fecha de expiración
                .signWith(getSignedKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512) // Firma con clave secreta y algoritmo HS512
                .compact(); // Finaliza y genera el token

        // Se devuelve el token con el prefijo "Bearer "
        return "Bearer " + token;
    }
}
