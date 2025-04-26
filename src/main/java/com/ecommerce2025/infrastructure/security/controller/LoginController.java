package com.ecommerce2025.infrastructure.security.controller;

import com.ecommerce2025.infrastructure.security.dtoSecurity.JWTClient;
import com.ecommerce2025.infrastructure.security.dtoSecurity.UserDTO;
import com.ecommerce2025.infrastructure.security.jwt.JWTGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar el inicio de sesión de usuarios.
 * <p>
 * REST controller to manage user login.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/security")
@Tag(name = "Seguridad / Security", description = "Endpoints relacionados con autenticación y generación de JWT / Endpoints related to authentication and JWT generation")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    /**
     * Constructor para inyectar dependencias.
     * <p>
     * Constructor to inject dependencies.
     * </p>
     *
     * @param authenticationManager gestor de autenticaciones / authentication manager
     * @param jwtGenerator generador de tokens JWT / JWT token generator
     */
    public LoginController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * Endpoint para realizar login y obtener un token JWT.
     * <p>
     * Endpoint to perform login and obtain a JWT token.
     * </p>
     *
     * @param userDTO DTO que contiene el nombre de usuario y la contraseña / DTO containing username and password
     * @return ResponseEntity que contiene el token JWT / ResponseEntity containing the JWT token
     */
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión y generar JWT / Log in and generate JWT",
            description = "Este endpoint autentica al usuario y genera un token JWT en caso de éxito. / This endpoint authenticates the user and generates a JWT token if successful."
    )
    public ResponseEntity<JWTClient> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Rol del usuario: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElse(null));

        String token = jwtGenerator.getToken(userDTO.username());
        JWTClient jwtClient = new JWTClient(token);

        return new ResponseEntity<>(jwtClient, HttpStatus.OK);
    }
}
