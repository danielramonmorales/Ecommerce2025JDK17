package com.ecommerce2025.infrastructure.security.controller;

import com.ecommerce2025.application.RegistrationService;
import com.ecommerce2025.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el registro de nuevos usuarios en la aplicación.
 * <p>
 * REST controller for registering new users into the application.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/security")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor para inyectar dependencias.
     * <p>
     * Constructor to inject dependencies.
     * </p>
     *
     * @param registrationService servicio de registro / registration service
     * @param passwordEncoder codificador de contraseñas / password encoder
     */
    public RegistrationController(RegistrationService registrationService, BCryptPasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * <p>
     * Endpoint to register a new user.
     * </p>
     *
     * @param user objeto usuario recibido en el cuerpo de la solicitud / user object received in the request body
     * @return ResponseEntity con el usuario registrado / ResponseEntity with the registered user
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        log.info("Clave encriptada: {}", passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(registrationService.register(user), HttpStatus.CREATED);
    }
}
