package com.ecommerce2025.infrastructure.dto.entity;

import jakarta.persistence.*; // Importa las clases necesarias para la persistencia en JPA
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data; // Lombok para generar automáticamente los métodos getter, setter, toString, etc.
import lombok.NoArgsConstructor; // Lombok para crear un constructor sin argumentos
import org.hibernate.annotations.CreationTimestamp; // Anotación de Hibernate para la fecha de creación
import org.hibernate.annotations.UpdateTimestamp; // Anotación de Hibernate para la fecha de actualización


import java.time.LocalDateTime; // Importa la clase para manejar fechas y horas

@Entity // Marca esta clase como una entidad JPA que será mapeada a una tabla de la base de datos
@Table(name = "categories") // Define el nombre de la tabla en la base de datos
@Data // Lombok: genera automáticamente getters, setters, equals, hashcode y toString
@NoArgsConstructor // Lombok: genera un constructor sin argumentos
public class CategoryEntity {

    @Id // Marca este campo como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se auto-generará el valor de este campo en la base de datos
    private Integer id; // El ID de la categoría

    @NotNull(message = "El nombre de la categoría no puede ser nulo") // Validamos que el nombre no sea nulo
    @Size(min = 2, max = 100, message = "El nombre de la categoría debe tener entre 2 y 100 caracteres") // Validamos el tamaño del nombre
    private String name; // El nombre de la categoría

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres") // Validamos que la descripción no sea demasiado larga
    private String description; // La descripción de la categoría

    @CreationTimestamp // Genera automáticamente el valor de esta columna con la fecha y hora actuales cuando la entidad se crea
    private LocalDateTime dateCreated; // Fecha de creación de la categoría

    @UpdateTimestamp // Genera automáticamente el valor de esta columna con la fecha y hora actuales cuando la entidad se actualiza
    private LocalDateTime dateUpdated; // Fecha de la última actualización de la categoría
}
