package com.ecommerce2025.domain.port;

import com.ecommerce2025.domain.model.Order;

/**
 * Puerto de salida que define las operaciones de persistencia relacionadas con las órdenes.
 * Esta interfaz debe ser implementada por una clase adaptadora (por ejemplo, un repositorio JPA o una conexión a base de datos).
 */
public interface IOrderRepository {

    /**
     * Guarda una nueva orden o actualiza una existente.
     *
     * @param order Orden a guardar
     * @return La orden guardada, con su ID (si aplica)
     */
    Order save(Order order);

    /**
     * Busca una orden por su identificador.
     *
     * @param id IDde la orden
     * @return La orden encontrada, o null si no existe
     */
    Order findById(Integer id);

    /**
     * Recupera todas las órdenes almacenadas.
     *
     * @return Iterable con todas las órdenes
     */
    Iterable<Order> findAll();

    /**
     * Recupera todas las órdenes asociadas a un usuario específico.
     *
     * @param userId ID del usuario
     * @return Iterable con las órdenes del usuario
     */
    Iterable<Order> findByUserId(Integer userId);

    /**
     * Actualiza el estado de una orden dado su ID.
     *
     * @param id IDde la orden a actualizar
     * @param state Nuevo estado (por ejemplo: "ENVIADA", "ENTREGADA")
     */
    void updateStateById(Integer id, String state);
}
