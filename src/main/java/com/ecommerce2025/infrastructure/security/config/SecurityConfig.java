package com.ecommerce2025.infrastructure.security.config;


import com.ecommerce2025.infrastructure.security.jwt.JWTAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    public SecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(
                        cors -> cors.configurationSource(
                                request -> {
                                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                                    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
                                    corsConfiguration.setAllowedMethods(Arrays.asList("*"));
                                    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
                                    return corsConfiguration;
                                }
                        )).

                csrf(csrf-> csrf.disable()).authorizeHttpRequests(
                        aut -> aut.requestMatchers("/api/v1/admin/categories/**").permitAll()
                                .requestMatchers("/api/v1/admin/products/**").permitAll()
                                .requestMatchers("/api/v1/admin/products/search").permitAll()
                                .requestMatchers("/api/v1/orders/**").permitAll()
                                .requestMatchers("/api/v1/payments/success").permitAll()
                                .requestMatchers("/api/v1/payments/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/api/v1/home/**").permitAll()
                                .requestMatchers("/api/v1/security/**").permitAll()//.anyRequest().authenticated()
                ).addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

}
