package com.ecommerce2025.infrastructure.security.config;

import com.ecommerce2025.infrastructure.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación eCommerce 2025.
 * <p>
 * Esta clase configura la autorización basada en JWT, el codificador de contraseñas y los filtros de seguridad HTTP.
 * </p>
 *
 * Security configuration for the eCommerce 2025 application.
 * <p>
 * This class configures JWT-based authorization, password encoder, and HTTP security filters.
 * </p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Proporciona el bean de AuthenticationManager.
     *
     * Provides the AuthenticationManager bean.
     *
     * @param authenticationConfiguration configuración de autenticación / authentication configuration
     * @return AuthenticationManager
     * @throws Exception si ocurre un error / if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * Configures the HTTP Security Filter Chain.
     *
     * @param http objeto HttpSecurity para configurar / HttpSecurity object to configure
     * @return SecurityFilterChain configurada / configured SecurityFilterChain
     * @throws Exception si ocurre un error / if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()
                     /*   .requestMatchers("/api/v1/categories/**", "/api/v1/admin/products/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/orders/**", "/api/v1/payments/**").hasRole("USER")
                        .requestMatchers("/api/v1/home/**", "/api/v1/security/**").permitAll()
                        .anyRequest().authenticated()*/
                );
             //   .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proporciona un codificador de contraseñas usando BCrypt.
     *
     * Provides a password encoder bean using BCrypt hashing function.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
