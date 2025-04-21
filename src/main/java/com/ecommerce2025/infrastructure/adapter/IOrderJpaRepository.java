package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.OrderState;
import com.ecommerce2025.infrastructure.dto.entity.OrderEntity;
import com.ecommerce2025.infrastructure.dto.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio JPA que maneja la persistencia de órdenes usando Spring Data.
 * Implementa operaciones adicionales personalizadas como actualizar el estado de la orden.
 */
@Repository
public interface IOrderJpaRepository extends JpaRepository<OrderEntity, Integer> {

    /**
     * Actualiza el estado de una orden específica según su ID.
     * Esta operación es de escritura, por lo que requiere una transacción.
     *
     * @param id    IDde la orden a actualizar
     * @param state Nuevo estado que se desea establecer
     */
    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o SET o.orderState = :state WHERE o.id = :id")
    void updateStateById(Integer id, OrderState state);

    /**
     * Busca todas las órdenes asociadas a un usuario específico.
     *
     * @param userEntity Entidad del usuario relacionada con las órdenes
     * @return Iterable con las órdenes del usuario
     */
    Iterable<OrderEntity> findByUserEntity(UserEntity userEntity);
}
