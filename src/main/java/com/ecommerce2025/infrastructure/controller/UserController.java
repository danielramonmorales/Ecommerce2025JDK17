package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta clase es un controlador REST que maneja las solicitudes HTTP
@RequestMapping("/api/v1/users") // Establece la URL base para las rutas de este controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde un frontend en el puerto 4200 (Angular, por ejemplo)
public class UserController {

    private final UserService userService; // Servicio de usuario que maneja la lógica de negocio

    // Constructor para inyección de dependencias del UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Método para crear un nuevo usuario.
     * @param user El usuario a crear, proporcionado en el cuerpo de la solicitud.
     * @return El usuario creado.
     */
    @PostMapping // Mapeo para la creación de un nuevo usuario mediante POST
    public User save(@RequestBody User user) {
        // Llama al servicio para guardar el usuario y devuelve el usuario creado
        return userService.save(user);
    }

    /**
     * Método para obtener un usuario por su ID.
     * @param id El ID del usuario a obtener.
     * @return El usuario correspondiente con el ID proporcionado.
     */
    @GetMapping("/{id}") // Mapeo para obtener un usuario por su ID mediante GET
    public User findById(@PathVariable Integer id) {
        // Llama al servicio para obtener el usuario por su ID y devuelve el resultado
        return userService.findById(id);
    }
}
