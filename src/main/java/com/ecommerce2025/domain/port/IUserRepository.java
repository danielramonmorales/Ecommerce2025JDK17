package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.User;

/**
 * Puerto de acceso para operaciones relacionadas con la entidad User.
 * Define las reglas del dominio que permiten interactuar con el repositorio de usuarios.
 */
public interface IUserRepository {

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * @param user el usuario a guardar
     * @return el usuario persistido
     */
    User save(User user);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * @param email el correo electrónico del usuario
     * @return el usuario encontrado, o null si no existe
     */
    User findByEmail(String email);

    /**
     * Busca un usuario por su ID.
     * @param id el ID del usuario
     * @return el usuario encontrado
     */
    User findById(Integer id);
}
