package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.infrastructure.exception.UserNotFoundException;
import com.ecommerce2025.infrastructure.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public User save(@RequestBody @Valid User user) {
        if (user == null || user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new BadRequestException("El nombre del usuario no puede ser vacío.");
        }
        return userService.save(user);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los datos de un usuario usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado con ID: " + id);
        }
        return user;
    }
}
