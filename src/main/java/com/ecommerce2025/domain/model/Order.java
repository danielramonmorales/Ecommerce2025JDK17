package com.ecommerce2025.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden de compra realizada por un usuario en el sistema.
 * Contiene información sobre la fecha de creación, los productos ordenados,
 * el estado de la orden y el identificador del usuario que la realizó.
 */
@Data
public class Order {

    // ======> CABECERA: aquí se encuentra la información general de la orden del cliente.

    /** Identificador único de la orden */
    private Integer id;

    /** Fecha y hora en la que se creó la orden */
    private LocalDateTime dateCreated;

    /** Lista de productos incluidos en la orden */
    private List<OrderProduct> orderProducts;

    /** Estado actual de la orden (ej. NUEVA, EN_PROCESO, ENVIADA, ENTREGADA) */
    private OrderState orderState;

    /** Identificador del usuario que realizó la orden */
    private Integer userId;

    /**
     * Constructor por defecto. Inicializa la lista de productos para evitar null pointers.
     */
    public Order() {
        orderProducts = new ArrayList<>();
    }

    /**
     * Calcula el precio total de la orden sumando el total de cada producto.
     *
     * @return Precio total de la orden como BigDecimal
     */
    public BigDecimal getTotalOrderPrice() {
        return this.orderProducts.stream()
                .map(OrderProduct::getTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
