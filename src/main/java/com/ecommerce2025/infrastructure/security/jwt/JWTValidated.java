package com.ecommerce2025.infrastructure.security.jwt;

import com.ecommerce2025.infrastructure.security.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.ecommerce2025.infrastructure.security.jwt.Constants.*;

/**
 * Clase para validar el JWT y autenticar al usuario.
 * <p>
 * Class to validate the JWT and authenticate the user.
 * </p>
 */
public class JWTValidated {

    /**
     * Verifica si el token existe en la solicitud HTTP.
     * <p>
     * Verifies if the token exists in the HTTP request.
     * </p>
     *
     * @param request solicitud HTTP / HTTP request
     * @param response respuesta HTTP / HTTP response
     * @return true si el token existe y es válido, false en caso contrario / true if token exists and is valid, false otherwise
     */
    public static boolean tokenExists(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            return false;
        }
        return true;
    }

    /**
     * Valida el token JWT y devuelve sus reclamaciones (claims).
     * <p>
     * Validates the JWT token and returns its claims.
     * </p>
     *
     * @param request solicitud HTTP / HTTP request
     * @return reclamaciones del token / token claims
     */
    public static Claims JWTValid(HttpServletRequest request){
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_BEARER_PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey(SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Establece la autenticación del usuario en el flujo de Spring.
     * <p>
     * Sets the user authentication in the Spring security flow.
     * </p>
     *
     * @param claims reclamaciones del token JWT / JWT token claims
     * @param userDetailService servicio que carga los detalles del usuario / service that loads user details
     */
    public static void setAuthentication(Claims claims, UserDetailService userDetailService){
        // Cargar los detalles del usuario desde el servicio
        UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());

        // Crear un token de autenticación con las autoridades del usuario
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Establecer el contexto de seguridad con la autenticación
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
