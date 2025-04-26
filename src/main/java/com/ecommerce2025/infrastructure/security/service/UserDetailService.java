package com.ecommerce2025.infrastructure.security.service;

import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación personalizada del servicio UserDetailsService.
 * <p>
 * Custom implementation of UserDetailsService.
 * </p>
 */
@Slf4j
@Service
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    /**
     * Constructor que inyecta el servicio de usuario.
     * <p>
     * Constructor that injects the UserService.
     * </p>
     *
     * @param userService el servicio de usuario / the user service
     */
    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Carga un usuario por su nombre de usuario (en este caso, por el correo electrónico).
     * <p>
     * Loads a user by their username (in this case, by email).
     * </p>
     *
     * @param username nombre de usuario / username (email)
     * @return UserDetails del usuario cargado / UserDetails of the loaded user
     * @throws UsernameNotFoundException si no se encuentra el usuario / if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Cargando usuario por nombre de usuario: {}", username);

        // Buscar al usuario en la base de datos
        User user = userService.findByEmail(username);

        if (user == null) {
            log.error("Usuario no encontrado: {}", username);
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + username);
        }

        // Devolver el UserDetails con los roles
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getUserType().name()) // Asignar roles según el tipo de usuario
                .build();
    }
}
