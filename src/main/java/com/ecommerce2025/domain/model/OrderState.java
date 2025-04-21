package com.ecommerce2025.domain.model;

/**
 * Representa los posibles estados de una orden en el sistema de comercio electrónico.
 *
 * CANCELLED - La orden ha sido cancelada por el usuario o el sistema.
 * CONFIRMED - La orden ha sido confirmada y está en proceso de cumplimiento.
 */
public enum OrderState {
    CANCELLED, // Estado que indica que la orden fue cancelada.
    CONFIRMED  // Estado que indica que la orden fue confirmada.
}
