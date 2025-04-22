package com.ecommerce2025.infrastructure.security.service;

import com.ecommerce2025.application.UserService;
import com.ecommerce2025.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado que implementa la interfaz UserDetailsService de Spring Security.
 * Este servicio carga los detalles del usuario (como el correo electrónico y contraseña) a partir
 * de la base de datos y los utiliza para la autenticación en el sistema.
 */
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService; // Dependencia del servicio de usuario

    /**
     * Constructor que inyecta el servicio de usuario.
     *
     * @param userService Servicio que interactúa con los datos de usuario.
     */
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Carga los detalles de un usuario por su nombre de usuario (correo electrónico).
     *
     * @param username Correo electrónico del usuario que se quiere autenticar
     * @return Un objeto UserDetails que contiene los detalles del usuario.
     * @throws UsernameNotFoundException Si no se encuentra un usuario con ese correo electrónico.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Cargando detalles del usuario con username: {}", username);

        // Intentamos encontrar el usuario por su correo electrónico
        User user = userService.findByEmail(username);

        // Si el usuario no existe, lanzamos una excepción de tipo UsernameNotFoundException
        if (user == null) {
            log.error("Usuario no encontrado con el correo electrónico: {}", username);
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + username);
        }

        // Devolvemos un objeto UserDetails con la información del usuario (correo y roles)
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())  // Establecemos el correo electrónico como el nombre de usuario
                .password(user.getPassword())  // Establecemos la contraseña encriptada del usuario
                .roles(user.getUserType().name())  // Establecemos los roles (por ejemplo: ADMIN, USER)
                .build();
    }
}
