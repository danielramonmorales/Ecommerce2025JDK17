package com.ecommerce2025.infrastructure.dto.entity;

import jakarta.persistence.*; // Importa las clases necesarias para la persistencia en JPA
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data; // Lombok para generar automáticamente los métodos getter, setter, toString, etc.
import lombok.NoArgsConstructor; // Lombok para crear un constructor sin argumentos
import org.hibernate.annotations.CreationTimestamp; // Anotación de Hibernate para la fecha de creación
import org.hibernate.annotations.UpdateTimestamp; // Anotación de Hibernate para la fecha de actualización
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal; // Importa la clase BigDecimal para manejar valores numéricos exactos
import java.time.LocalDateTime; // Importa la clase para manejar fechas y horas

@Entity // Marca esta clase como una entidad JPA que será mapeada a una tabla de la base de datos
@Table(name = "products") // Define el nombre de la tabla en la base de datos
@Data // Lombok: genera automáticamente getters, setters, toString() y otros métodos útiles
@NoArgsConstructor // Lombok: genera un constructor sin argumentos
public class ProductEntity {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Seauto-generará el valor de este campo en la base de datos
    private Integer id; // El ID del producto

    @NotNull(message = "El nombre del producto no puede ser nulo") // Validamos que el nombre no sea nulo
    @NotEmpty(message = "El nombre del producto no puede estar vacío") // Validamos que el nombre no esté vacío
    @Size(min = 2, max = 100, message = "El nombre del producto debe tener entre 2 y 100 caracteres") // Longitud mínima y máxima para el nombre
    private String name; // El nombre del producto

    @NotNull(message = "El código del producto no puede ser nulo") // Validamos que el código no sea nulo
    @NotEmpty(message = "El código del producto no puede estar vacío") // Validamos que el código no esté vacío
    @Size(min = 2, max = 50, message = "El código del producto debe tener entre 2 y 50 caracteres") // Longitud del código
    private String code; // El código del producto (probablemente un identificador único o SKU)

    @Size(max = 500, message = "La descripción del producto no puede exceder los 500 caracteres") // Longitud máxima de la descripción
    private String description; // La descripción del producto

   // @URL(message = "La URL de la imagen no es válida") // Validamos que la URL de la imagen sea válida
    private String urlImage; // URL de la imagen del producto

    @Column(name = "image_public_id")
    private String imagePublicId;


    @NotNull(message = "El precio no puede ser nulo") // Validamos que el precio no sea nulo
    @Positive(message = "El precio debe ser mayor que cero") // Validamos que el precio sea positivo
    private BigDecimal price; // El precio del producto (utilizamos BigDecimal para precisión en valores numéricos)

    @CreationTimestamp // Se establece automáticamente la fecha de creación cuando se inserta el producto en la base de datos
    private LocalDateTime dateCreated; // Fecha de creación del producto

    @UpdateTimestamp // Se establece automáticamente la fecha de la última actualización cuando se actualiza el producto
    private LocalDateTime dateUpdated; // Fecha de la última actualización del producto

    // Relación de muchos a uno con la entidad UserEntity (probablemente el usuario que creó el producto)
    @ManyToOne(fetch = FetchType.LAZY) // Relación de muchos a uno, se obtiene solo cuando es necesario (fetch lazy)
    @JoinColumn(name = "user_id") // Especifica la columna en la tabla que será la clave foránea
    @NotNull(message = "El usuario asociado no puede ser nulo") // Validamos que el usuario no sea nulo
    private UserEntity userEntity; // Usuario relacionado con el producto

    // Relación de muchos a uno con la entidad CategoryEntity (la categoría a la que pertenece el producto)
    @ManyToOne(fetch = FetchType.LAZY) // Relación de muchos a uno, se obtiene solo cuando es necesario (fetch lazy)
    @JoinColumn(name = "category_id") // Especifica la columna en la tabla que será la clave foránea
    @NotNull(message = "La categoría no puede ser nula") // Validamos que la categoría no sea nula
    private CategoryEntity categoryEntity; // Categoría a la que pertenece el producto
}
