package com.ecommerce2025.infrastructure.dto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Entidad que representa un producto dentro de una orden específica.
 * Mapea la tabla intermedia 'order_products', con información de cantidad, precio y producto.
 */
@Data
@Entity
@Table(name = "order_products")
public class OrderProductEntity {

    /** Identificador único del registro de producto en la orden (clave primaria, autogenerada) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Cantidad del producto solicitada en esta orden */
    @NotNull(message = "La cantidad no puede ser nula") // Validamos que la cantidad no sea nula
    @Positive(message = "La cantidad debe ser mayor que cero") // Validamos que la cantidad sea positiva
    private BigDecimal quantity;

    /** Precio unitario del producto al momento de la orden */
    @NotNull(message = "El precio no puede ser nulo") // Validamos que el precio no sea nulo
    @Positive(message = "El precio debe ser mayor que cero") // Validamos que el precio sea positivo
    private BigDecimal price;

    /** IDdel producto asociado (referencia indirecta, no FK explícita a tabla productos) */
    @NotNull(message = "El ID del producto no puede ser nulo") // Validamos que el productId no sea nulo
    private Integer productId;

    /**
     * Orden a la que pertenece este producto.
     * Muchas líneas de productos pueden pertenecer a una sola orden.
     */
    @ManyToOne
    @NotNull(message = "La orden no puede ser nula") // Validamos que la orden asociada no sea nula
    private OrderEntity orderEntity;
}
