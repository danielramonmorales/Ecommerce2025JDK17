package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio para manejar las operaciones relacionadas con los usuarios.
 * Permite la creación, búsqueda y recuperación de información de usuarios.
 */
@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Guarda un nuevo usuario en el repositorio.
     * @param user el usuario a guardar
     * @return el usuario guardado
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Recupera un usuario por su ID.
     * @param id el ID del usuario
     * @return el usuario encontrado
     * @throws RuntimeException si no se encuentra el usuario con el ID proporcionado
     */
    public User findById(Integer id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
        return user;
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @param email el correo electrónico del usuario
     * @return el usuario encontrado
     * @throws RuntimeException si no se encuentra el usuario con el correo proporcionado
     */
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario con correo " + email + " no encontrado.");
        }
        return user;
    }
}
