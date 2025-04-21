package com.ecommerce2025.infrastructure.dto.entity;

import com.ecommerce2025.domain.model.UserType; // Importa la clase UserType que probablemente enumera los tipos de usuarios
import jakarta.persistence.*; // Importa las clases necesarias para la persistencia en JPA
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor; // Lombok para generar un constructor con todos los argumentos
import lombok.Data; // Lombok para generar automáticamente los métodos getter, setter, toString, etc.
import lombok.NoArgsConstructor; // Lombok para generar un constructor sin argumentos
import org.hibernate.annotations.CreationTimestamp; // Anotación de Hibernate para la fecha de creación
import org.hibernate.annotations.UpdateTimestamp; // Anotación de Hibernate para la fecha de actualización
import java.time.LocalDateTime; // Importa la clase para manejar fechas y horas

@Entity // Marca esta clase como una entidad JPA que será mapeada a una tabla de la base de datos
@Table(name = "users") // Define el nombre de la tabla en la base de datos
@Data // Lombok: genera automáticamente getters, setters, toString() y otros métodos útiles
@AllArgsConstructor // Lombok: genera un constructor con todos los argumentos
@NoArgsConstructor // Lombok: genera un constructor sin argumentos
public class UserEntity {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos se encargará de autogenerar el valor de este campo
    private Integer id; // El ID del usuario

    @NotNull(message = "El nombre de usuario no puede ser nulo") // Validamos que el nombre de usuario no sea nulo
    @NotEmpty(message = "El nombre de usuario no puede estar vacío") // Validamos que el nombre de usuario no esté vacío
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres") // Longitud mínima y máxima para el nombre de usuario
    private String username; // El nombre de usuario

    @NotNull(message = "El primer nombre no puede ser nulo") // Validamos que el primer nombre no sea nulo
    @NotEmpty(message = "El primer nombre no puede estar vacío") // Validamos que el primer nombre no esté vacío
    private String firstName; // El primer nombre del usuario

    @NotNull(message = "El apellido no puede ser nulo") // Validamos que el apellido no sea nulo
    @NotEmpty(message = "El apellido no puede estar vacío") // Validamos que el apellido no esté vacío
    private String lastName; // El apellido del usuario

    @Column(unique = true) // Asegura que el campo email sea único en la base de datos
    @Email(message = "El correo electrónico debe tener un formato válido") // Validamos que el email tenga un formato correcto
    private String email; // El correo electrónico del usuario

    private String address; // La dirección del usuario

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El número de celular no tiene un formato válido") // Validamos que el celular tenga un formato adecuado (expresión regular internacional)
    private String cellphone; // El número de celular del usuario

    @NotNull(message = "La contraseña no puede ser nula") // Validamos que la contraseña no sea nula
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") // Validamos que la contraseña tenga al menos 6 caracteres
    private String password; // La contraseña del usuario

    @Enumerated(EnumType.STRING) // Especifica que el valor de 'userType' debe almacenarse como una cadena (su nombre en el enum)
    private UserType userType; // El tipo de usuario (probablemente 'admin', 'customer', etc.)

    @CreationTimestamp // Se establece automáticamente la fecha de creación cuando se inserta el usuario en la base de datos
    private LocalDateTime dateCreated; // Fecha de creación del usuario

    @UpdateTimestamp // Se establece automáticamente la fecha de la última actualización cuando se actualiza el usuario
    private LocalDateTime dateUpdate; // Fecha de la última actualización del usuario

}
