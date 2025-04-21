package com.ecommerce2025.infrastructure.dto.entity;

import jakarta.persistence.*;
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
    private BigDecimal quantity;

    /** Precio unitario del producto al momento de la orden */
    private BigDecimal price;

    /** IDdel producto asociado (referencia indirecta, no FK explícita a tabla productos) */
    private Integer productId;

    /**
     * Orden a la que pertenece este producto.
     * Muchas líneas de productos pueden pertenecer a una sola orden.
     */
    @ManyToOne
    private OrderEntity orderEntity;
}
