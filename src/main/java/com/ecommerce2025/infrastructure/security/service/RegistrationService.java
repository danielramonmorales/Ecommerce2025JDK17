package com.ecommerce2025.infrastructure.security.service;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de registrar un nuevo usuario en el sistema.
 * Este servicio verifica si el usuario ya existe, encripta su contraseña
 * y luego lo guarda en la base de datos.
 */
@Service
public class RegistrationService {

    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Para encriptar la contraseña

    /**
     * Constructor que inyecta el repositorio de usuarios y el codificador de contraseñas.
     *
     * @param iUserRepository El repositorio donde se guardarán los usuarios.
     * @param passwordEncoder El codificador de contraseñas.
     */
    public RegistrationService(IUserRepository iUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema. Verifica si el correo electrónico
     * ya está registrado, encripta la contraseña y guarda al usuario.
     *
     * @param user El usuario que se desea registrar.
     * @return El usuario registrado.
     * @throws IllegalArgumentException Si el usuario ya está registrado con el mismo correo electrónico.
     */
    public User register(User user) throws IllegalArgumentException {
        // Verifica si el correo electrónico ya está registrado
       /* if (iUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }*/

        // Encripta la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Guarda al usuario en la base de datos
        return iUserRepository.save(user);
    }
}
