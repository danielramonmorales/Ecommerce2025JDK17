package com.ecommerce2025.infrastructure.security.jwt;

import com.ecommerce2025.infrastructure.security.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.ecommerce2025.infrastructure.security.jwt.Constants.*;

/**
 * Clase responsable de validar el token JWT y gestionar la autenticación del usuario.
 * Se encarga de verificar que el token esté presente en la solicitud y que sea válido.
 * También se encarga de establecer el usuario autenticado en el contexto de seguridad de Spring.
 */
public class JWTValidate {

    /**
     * Verifica si el token JWT está presente en la solicitud HTTP.
     *
     * @param request Solicitud HTTP entrante
     * @param response Respuesta HTTP
     * @return true si el token existe en el encabezado Authorization, false en caso contrario
     */
    public static boolean tokenExists(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // Verifica si el header 'Authorization' existe y si empieza con el prefijo 'Bearer '
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX))
            return false;

        return true;
    }

    /**
     * Validay parsea el token JWT extraído de la solicitud HTTP.
     *
     * @param request Solicitud HTTP entrante
     * @return Claims extraídos del token JWT
     * @throws io.jsonwebtoken.ExpiredJwtException Si el token ha expirado
     * @throws io.jsonwebtoken.MalformedJwtException Si el token está mal formado
     * @throws io.jsonwebtoken.UnsupportedJwtException Si el tipo de token no es soportado
     */
    public static Claims JWTValid(HttpServletRequest request) {
        // Obtiene el token JWT del encabezado 'Authorization' (sin el prefijo 'Bearer ')
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_BEARER_PREFIX, "");

        // Parsear el token JWT con la clave secreta para validar su integridad
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey(SUPER_SECRET_KEY)) // Se usa la clave secreta para firmar el token
                .build()
                .parseClaimsJws(jwtToken) // Parseamos el JWT y obtenemos las "claims"
                .getBody(); // Devuelve el cuerpo (claims) del token
    }

    /**
     * Establece la autenticación del usuario en el contexto de seguridad de Spring.
     *
     * @param claims Información extraída del token JWT
     * @param customUserDetailService Servicio personalizado que carga los detalles del usuario
     */
    public static void setAuthentication(Claims claims, CustomUserDetailService customUserDetailService) {
        // Cargar los detalles del usuario desde el servicio de detalles personalizados
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());

        // Crear el objeto de autenticación con los detalles del usuario
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
