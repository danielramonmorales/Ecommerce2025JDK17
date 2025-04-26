package com.ecommerce2025.infrastructure.security.jwt;

import com.ecommerce2025.infrastructure.security.service.UserDetailService;
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

import static com.ecommerce2025.infrastructure.security.jwt.JWTValidated.*;

/**
 * Filtro de autorización JWT que intercepta todas las solicitudes HTTP
 * yvalida el token de acceso.
 * <p>
 * JWT Authorization Filter that intercepts all HTTP requests
 * and validates the access token.
 * </p>
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailService userDetailService;

    /**
     * Constructor para inyectar UserDetailService.
     * <p>
     * Constructor to inject UserDetailService.
     * </p>
     *
     * @param userDetailService servicio que carga los detalles del usuario / service that loads user details
     */
    public JwtAuthorizationFilter(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    /**
     * Método que intercepta cada solicitud HTTP para validar el JWT.
     * <p>
     * Method that intercepts every HTTP request to validate the JWT.
     * </p>
     *
     * @param request petición HTTP / HTTP request
     * @param response respuesta HTTP / HTTPresponse
     * @param filterChain cadena de filtros de Spring / Spring filter chain
     * @throws ServletException excepción de servlet / servlet exception
     * @throws IOException excepción de entrada/salida / input/output exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (tokenExists(request, response)) {
                Claims claims = JWTValid(request);
                if (claims.get("authorities") != null) {
                    setAuthentication(claims, userDetailService);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido o expirado / Invalid or expired token");
        } catch (SecurityException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token mal formado o con datos no válidos / Malformed or invalid token");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor / Internal server error");
        }
    }
}
