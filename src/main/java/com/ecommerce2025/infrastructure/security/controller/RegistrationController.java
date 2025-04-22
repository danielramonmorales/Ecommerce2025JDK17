package com.ecommerce2025.infrastructure.security.controller;


import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.infrastructure.security.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationController(RegistrationService registrationService, BCryptPasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param user Objeto con los datos del usuario a registrar
     * @return El usuario creado junto con un código 201 (CREATED)
     */
    @PostMapping("/register")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Registro de nuevo usuario",
            description = "Registra un nuevo usuario en la plataforma."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "Usuario registrado exitosamente."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Datos inválidos para el registro."
    )
    public ResponseEntity<User> register(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Encriptar contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = registrationService.register(user);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}

