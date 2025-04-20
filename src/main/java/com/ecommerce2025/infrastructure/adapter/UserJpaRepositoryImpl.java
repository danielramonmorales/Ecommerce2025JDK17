package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import com.ecommerce2025.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component // Anotación para marcar esta clase como un componente gestionado por Spring
public class UserJpaRepositoryImpl implements IUserRepository {

    // Repositorio JPA para interactuar con la base de datos para operaciones de usuarios
    private final IUserJpaRepository iUserJpaRepository;

    // Mapper para convertir entre el modelo de dominio 'User' y la entidad JPA 'UserEntity'
    private final UserMapper userMapper;

    // Constructor que inyecta las dependencias del repositorio y el mapper
    public UserJpaRepositoryImpl(IUserJpaRepository iUserJpaRepository, UserMapper userMapper) {
        this.iUserJpaRepository = iUserJpaRepository;
        this.userMapper = userMapper;
    }

    /**
     * Guarda un usuario en la base de datos.
     * Convierte el objeto de dominio a una entidad JPA y guarda el usuario utilizando el repositorio JPA.
     * @param user El usuario a guardar
     * @return El usuario guardado convertido de nuevo a un objeto de dominio
     */
    @Override
    public User save(User user) {
        // Convertir el usuario del modelo de dominio a una entidad JPA y guardarlo
        return userMapper.toUser(iUserJpaRepository.save(userMapper.toUserEntity(user)));
    }

    /**
     * Busca un usuario por su correo electrónico.
     * Si no se encuentra, lanza una excepción.
     * @param email El correo electrónico del usuario
     * @return El usuario correspondiente al correo electrónico, convertido a un objeto de dominio
     * @throws RuntimeException Si no se encuentra el usuario
     */
    @Override
    public User findByEmail(String email) {
        // Buscar el usuario por su correo electrónico y convertirlo al modelo de dominio
        // Si no se encuentra, se lanza una excepción RuntimeException
        return userMapper.toUser(iUserJpaRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " no encontrado")
        ));
    }

    /**
     * Busca un usuario por su ID.
     * Si no se encuentra, lanza una excepción.
     * @param id El ID del usuario
     * @return El usuario correspondiente al ID, convertido a un objeto de dominio
     * @throws RuntimeException Si no se encuentra el usuario
     */
    @Override
    public User findById(Integer id) {
        // Buscar el usuario por su ID y convertirlo al modelo de dominio
        // Si no se encuentra, se lanza una excepción RuntimeException
        return userMapper.toUser(
                iUserJpaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no existe"))
        );
    }
}
