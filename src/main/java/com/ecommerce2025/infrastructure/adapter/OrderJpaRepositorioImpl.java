package com.ecommerce2025.infrastructure.adapter;

import com.ecommerce2025.domain.model.Order;
import com.ecommerce2025.domain.model.OrderState;
import com.ecommerce2025.domain.port.IOrderRepository;
import com.ecommerce2025.infrastructure.dto.entity.OrderEntity;
import com.ecommerce2025.infrastructure.dto.entity.UserEntity;
import com.ecommerce2025.infrastructure.mapper.OrderMapper;
import org.springframework.stereotype.Component;

/**
 * Implementación de IOrderRepository que utiliza JPA para acceder a la base de datos.
 * Actúa como adaptador de salida dentro de la arquitectura hexagonal.
 */
@Component
public class OrderJpaRepositorioImpl implements IOrderRepository {

    private final OrderMapper orderMapper;
    private final IOrderJpaRepository iOrderJpaRepository;

    /**
     * Constructor que recibe el mapper y el repositorio JPA.
     *
     * @param orderMapper Mapper que convierte entre modelo de dominio y entidad JPA
     * @param iOrderJpaRepository Repositorio JPA para acceder a la base de datos
     */
    public OrderJpaRepositorioImpl(OrderMapper orderMapper, IOrderJpaRepository iOrderJpaRepository) {
        this.orderMapper = orderMapper;
        this.iOrderJpaRepository = iOrderJpaRepository;
    }

    /**
     * Guarda una orden, asegurando que las relaciones estén correctamente seteadas.
     *
     * @param order Orden a guardar
     * @return Orden guardada con su ID generado
     */
    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toOrderEntity(order);

        // Aseguramos la relación bidireccional entre OrderEntity y OrderProductEntity
        orderEntity.getOrderProducts().forEach(
                orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );

        return orderMapper.toOrder(iOrderJpaRepository.save(orderEntity));
    }

    /**
     * Busca una orden por su ID. Lanza excepción si no existe.
     *
     * @param id IDde la orden
     * @return Orden encontrada
     */
    @Override
    public Order findById(Integer id) {
        return orderMapper.toOrder(iOrderJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order con id: " + id + " no existe")
        ));
    }

    /**
     * Recupera todas las órdenes en la base de datos.
     *
     * @return Lista de órdenes
     */
    @Override
    public Iterable<Order> findAll() {
        return orderMapper.toOrderList(iOrderJpaRepository.findAll());
    }

    /**
     * Busca todas las órdenes de un usuario a partir de su ID.
     *
     * @param userId ID del usuario
     * @return Lista de órdenes asociadas al usuario
     */
    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return orderMapper.toOrderList(iOrderJpaRepository.findByUserEntity(userEntity));
    }

    /**
     * Actualiza el estado de una orden por su ID.
     * Solo admite los estados CANCELLED y CONFIRMED (puede expandirse).
     *
     * @param id IDde la orden
     * @param state Estado como cadena
     */
    @Override
    public void updateStateById(Integer id, String state) {
        // Se recomienda validar bien los posibles estados aquí
        if (OrderState.CANCELLED.name().equalsIgnoreCase(state)) {
            iOrderJpaRepository.updateStateById(id, OrderState.CANCELLED);
        } else {
            iOrderJpaRepository.updateStateById(id, OrderState.CONFIRMED);
        }
    }
}
