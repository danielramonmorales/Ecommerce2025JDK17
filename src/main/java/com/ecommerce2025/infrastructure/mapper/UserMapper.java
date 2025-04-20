package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.User; // Importa la clase de dominio User
import com.ecommerce2025.infrastructure.dto.entity.UserEntity; // Importa la clase de entidad UserEntity
import org.mapstruct.InheritConfiguration; // Anotación para usar la configuración del mapeo inverso
import org.mapstruct.Mapper; // Anotación de MapStruct para crear el mapper
import org.mapstruct.Mapping; // Anotación de MapStruct para mapear campos específicos
import org.mapstruct.Mappings; // Anotación que permite la agrupación de múltiples mapeos

// La anotación @Mapper indica que esta interfaz es un Mapper que MapStruct va a procesar para generar la implementación.
// El atributo componentModel = "spring" permite que MapStruct registre la clase generada como un componente Spring,
// de modo que se pueda inyectar como un servicio en cualquier parte de la aplicación.
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte una instancia de UserEntity (entidad JPA) a User (modelo de dominio).
     *
     * @param userEntity La entidad UserEntity que se va a convertir.
     * @return El modelo de dominio User.
     */
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"), // Mapea el campo 'id' de UserEntity a 'id' de User
                    @Mapping(source = "username", target = "username"), // Mapea 'username'
                    @Mapping(source = "firstName", target = "firstName"), // Mapea 'firstName'
                    @Mapping(source = "lastName", target = "lastName"), // Mapea 'lastName'
                    @Mapping(source = "email", target = "email"), // Mapea 'email'
                    @Mapping(source = "address", target = "address"), // Mapea 'address'
                    @Mapping(source = "cellphone", target = "cellphone"), // Mapea 'cellphone'
                    @Mapping(source = "password", target = "password"), // Mapea 'password'
                    @Mapping(source = "userType", target = "userType"), // Mapea 'userType'
                    @Mapping(source = "dateCreated", target = "dateCreated"), // Mapea 'dateCreated'
                    @Mapping(source = "dateUpdate", target = "dateUpdate") // Mapea 'dateUpdate'
            }
    )
    User toUser(UserEntity userEntity); // Convierte una instancia de UserEntity a User

    /**
     * Convierte una lista de UserEntity a una lista de User.
     *
     * @param userEntities El iterable de UserEntity que se va a convertir.
     * @return Una lista de User.
     */
    Iterable<User> toUsers(Iterable<UserEntity> userEntities);

    /**
     * Convierte una instancia de User (modelo de dominio) a UserEntity (entidad JPA).
     *
     * @param user El modelo de dominio User que se va a convertir.
     * @return La entidad UserEntity.
     */
    @InheritConfiguration // Reutiliza la configuración del método toUser para el mapeo inverso.
    UserEntity toUserEntity(User user); // Convierte una instancia de User a UserEntity
}
