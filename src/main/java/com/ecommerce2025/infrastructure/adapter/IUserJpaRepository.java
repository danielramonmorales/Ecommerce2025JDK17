package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.infrastructure.dto.entity.UserEntity; // Importa la entidad que representa a un usuario
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository, que proporciona métodos CRUD básicos
import org.springframework.stereotype.Repository; // Importa la anotación que marca la clase como un repositorio

import java.util.Optional; // Importa Optional para envolver el resultado de búsqueda de un usuario

/**
 * Interfaz para interactuar con la base de datos y realizar operaciones CRUD sobre los usuarios.
 * Esta interfaz extiende JpaRepository, lo que proporciona implementación automática de los métodos básicos
 * para trabajar con la entidad UserEntity, además de un método personalizado para buscar un usuario por correo electrónico.
 */
@Repository // Marca la interfaz como un componente de repositorio en Spring.
public interface IUserJpaRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Devuelve un Optional para manejar casos en los que el usuario no exista en la base de datos.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no se encuentra.
     * Optional<UserEntity>: Se utiliza un Optional para manejar la posibilidad de que no se encuentre un usuario con el correo
     * electrónico dado. Si el usuario no existe, el Optional estará vacío (Optional.empty()), lo que evita la posibilidad de
     * obtener un null y facilita el manejo de este caso.
     */
    Optional<UserEntity> findByEmail(String email);
}
