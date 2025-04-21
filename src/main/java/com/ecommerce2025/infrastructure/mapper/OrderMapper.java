package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.Order;
import com.ecommerce2025.infrastructure.dto.entity.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper para convertir entre entidades de base de datos {@link OrderEntity}
 * y modelos de dominio {@link Order}.
 * <p>
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo,
 * y se integra con Spring mediante {@code componentModel = "spring"}, lo que permite
 * inyectarlo como un bean en el contexto de Spring. Además, este mapper hace uso de
 * {@link OrderProductMapper} para mapear los productos de la orden.
 */
@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public interface OrderMapper {

    /**
     * Convierte una entidad {@link OrderEntity} a un modelo de dominio {@link Order}.
     * <p>
     * El mapeo se realiza entre los campos correspondientes de ambas clases, incluyendo
     * una conversión especial para {@link } mediante el uso del {@link OrderProductMapper}.
     *
     * @param orderEntity la entidad de orden proveniente de la base de datos
     * @return el modelo de dominio correspondiente
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "dateCreated", target = "dateCreated"),
            @Mapping(source = "orderProducts", target = "orderProducts"),
            @Mapping(source = "orderState", target = "orderState"),
            @Mapping(source = "userEntity.id", target = "userId") // Convierte la ID del usuario
    })
    Order toOrder(OrderEntity orderEntity);

    /**
     * Convierte una lista iterable de entidades {@link OrderEntity}
     * a una lista iterable de modelos de dominio {@link Order}.
     *
     * @param orderEntities lista de entidades de orden a convertir
     * @return lista iterable de modelos de dominio convertidos
     */
    Iterable<Order> toOrderList(Iterable<OrderEntity> orderEntities);

    /**
     * Convierte un modelo de dominio {@link Order} a una entidad {@link OrderEntity}.
     * <p>
     * Este método utiliza la anotación {@link InheritInverseConfiguration} para reutilizar
     * el mapeo inverso de la conversión a {@link OrderEntity}.
     *
     * @param order el modelo de dominio a convertir
     * @return la entidad correspondiente para persistencia
     */
    @InheritInverseConfiguration
    OrderEntity toOrderEntity(Order order);

}
