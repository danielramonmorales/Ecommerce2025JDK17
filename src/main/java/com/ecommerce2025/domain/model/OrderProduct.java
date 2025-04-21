package com.ecommerce2025.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Representa un producto incluido dentro de una orden.
 * Contiene información sobre la cantidad, precio unitario, y el ID del producto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProduct {

    // ======> DETALLE: esta clase representa el detalle de cada producto en la orden

    /** Identificador único del detalle de la orden (por ejemplo, línea de orden #10) */
    private Integer id;

    /** Cantidad del producto que se ordenó (ej. 2 unidades) */
    private BigDecimal quantity;

    /** Precio unitario del producto (ej. $299.00) */
    private BigDecimal price;

    /** IDdel producto que fue ordenado (referencia al catálogo de productos) */
    private Integer productId;

    /**
     * Calcula el total del ítem multiplicando el precio por la cantidad.
     *
     * @return Total del producto en esta línea de orden.
     */
    public BigDecimal getTotalItem() {
        return this.price.multiply(quantity);
    }
}
