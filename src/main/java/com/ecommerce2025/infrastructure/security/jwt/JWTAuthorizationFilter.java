package com.ecommerce2025.infrastructure.security.jwt;

import com.ecommerce2025.infrastructure.security.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ecommerce2025.infrastructure.security.jwt.JWTValidate.*;

/**
 * Filtro personalizado para autorizar solicitudes HTTP utilizando JWT.
 * Este filtro se ejecuta una sola vez por solicitud (OncePerRequestFilter)
 * y validaque el token JWT sea correcto antes de permitir el acceso al recurso.
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;

    /**
     * Constructor que inyecta el servicio de usuarios.
     *
     * @param customUserDetailService Servicio personalizado para cargar detalles del usuario
     */
    public JWTAuthorizationFilter(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    /**
     * Método principal que intercepta cada solicitud HTTP.
     * Verifica si existe un token, lo valida, y si es correcto
     * establece la autenticación en el contexto de seguridad.
     *
     * @param request  solicitud HTTP
     * @param response respuesta HTTP
     * @param filterChain cadena de filtros
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // Verifica si el token existe y es válido
            if (tokenExists(request, response)) {
                Claims claims = JWTValid(request);

                // Si el token tiene autoridades (roles), se configura la autenticación
                if (claims.get("authorities") != null) {
                    setAuthentication(claims, customUserDetailService);
                } else {
                    // Si no tiene roles, se limpia el contexto de seguridad
                    SecurityContextHolder.clearContext();
                }

            } else {
                // Si no hay token, se limpia el contexto (usuario no autenticado)
                SecurityContextHolder.clearContext();
            }

            // Continúa con el siguiente filtro en la cadena
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            // Manejo de errores por token expirado, no soportado o mal formado
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
