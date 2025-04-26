package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para manejar el registro de usuarios.
 * <p>
 * Service to handle user registration.
 * </p>
 */
@Slf4j
public class RegistrationService {

    private final IUserRepository userRepository;

    /**
     * Constructor para inyectar el repositorio de usuarios.
     * <p>
     * Constructor to inject the user repository.
     * </p>
     *
     * @param userRepository el repositorio de usuarios / the user repository
     */
    public RegistrationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Registers a new user in the system.
     * </p>
     *
     * @param user el usuario a registrar / the user to register
     * @return el usuario registrado / the registered user
     * @throws IllegalArgumentException si el correo electrónico ya está en uso / if the email is already in use
     */
    public User register(User user) throws IllegalArgumentException {
        // Verificar si el correo electrónico ya está en uso
        if (isEmailAlreadyTaken(user.getEmail())) {
            log.error("El correo electrónico ya está registrado: {}", user.getEmail());
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Guardar el nuevo usuario
        User savedUser = userRepository.save(user);
        log.info("Usuario registrado exitosamente: {}", savedUser.getEmail());
        return savedUser;
    }

    /**
     * Verifica si el correo electrónico ya está registrado en el sistema.
     * <p>
     * Checks if the email is already registered in the system.
     * </p>
     *
     * @param email el correo electrónico a verificar / the email to check
     * @return true si el correo electrónico ya está registrado / true if the email is already registered
     */
    private boolean isEmailAlreadyTaken(String email) {
        return userRepository.findByEmail(email) != null; // Asumiendo que el repositorio tiene este método
    }
}
