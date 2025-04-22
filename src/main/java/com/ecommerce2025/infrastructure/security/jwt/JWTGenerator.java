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
 * Servicio encargado de la generación de tokens JWT para la autenticación y autorización de los usuarios.
 * El token contiene el nombre de usuario, los roles del usuario, fecha de emisión y expiración.
 */
@Service
public class JWTGenerator {

    /**
     * Genera un token JWT firmado que contiene el nombre de usuario, roles y una fecha de expiración.
     *
     * @param username Nombre de usuario (correo electrónico o nombre de usuario)
     * @return Token JWT en formato Bearer (ejemplo: Bearer <token>)
     */
    public String getToken(String username) {

        // Obtención de los roles/autoridades del usuario desde el contexto de seguridad
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().toString()
        );

        // Creación del token JWT
        String token = Jwts.builder()
                .setId("ecommerce") // Identificador del JWT
                .setSubject(username) // El sujeto es el nombre de usuario
                .claim("authorities", authorityList.stream()
                        .map(GrantedAuthority::getAuthority) // Extrae los roles/autoridades del usuario
                        .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)) // Fecha de expiración
                .signWith(getSignedKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512) // Firma del token con la clave secreta
                .compact(); // Genera el token compactado

        // Devuelve el token con el prefijo "Bearer "
        return "Bearer " + token;
    }
}
