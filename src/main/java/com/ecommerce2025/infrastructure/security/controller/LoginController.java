package com.ecommerce2025.infrastructure.security.controller;


import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.infrastructure.security.dto.JWTClient;
import com.ecommerce2025.infrastructure.security.dto.UserDTO;
import com.ecommerce2025.infrastructure.security.jwt.JWTGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    /**
     * Autenticaun usuario y devuelve un token JWT si las credenciales son válidas.
     *
     * @param userDTO Objeto con las credenciales del usuario (email y contraseña)
     * @return JWTClient con el token generado y datos del usuario
     */
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Inicio de sesión de usuario",
            description = "Autentica al usuario y devuelve un JWT si las credenciales son correctas."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Inicio de sesión exitoso. Se devuelve el JWT."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401", description = "Credenciales incorrectas."
    )
    public ResponseEntity<JWTClient> login(@RequestBody UserDTO userDTO) {
        try {
            // Autenticación del usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password())
            );

            // Establece el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Usuario autenticado: {}", authentication.getName());
            log.info("Rol del usuario: {}", authentication.getAuthorities().stream().findFirst().orElse(null));

            // Buscar información adicional del usuario
            User user = userService.findByEmail(userDTO.username());

            // Generar el token JWT
            String token = jwtGenerator.getToken(userDTO.username());

            // Construir la respuesta
            JWTClient jwtClient = new JWTClient(user.getId(), token, user.getUserType().toString());
            return new ResponseEntity<>(jwtClient, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Error en autenticación: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}


