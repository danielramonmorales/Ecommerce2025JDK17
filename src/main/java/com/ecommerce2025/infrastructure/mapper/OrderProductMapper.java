package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.OrderProduct;
import com.ecommerce2025.infrastructure.dto.entity.OrderProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper para convertir entre entidades de base de datos {@link OrderProductEntity}
 * y modelos de dominio {@link OrderProduct}.
 * <p>
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo.
 * Se integra con Spring mediante {@code componentModel = "spring"}, lo que permite
 * inyectarlo como un bean en el contexto de Spring.
 */
@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    /**
     * Convierte una entidad {@link OrderProductEntity} a un modelo de dominio {@link OrderProduct}.
     *
     * @param orderProductEntity la entidad de producto en orden proveniente de la base de datos
     * @return el modelo de dominio correspondiente
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "productId", target = "productId")
    })
    OrderProduct toOrderProduct(OrderProductEntity orderProductEntity);

    /**
     * Convierte una lista iterable de entidades {@link OrderProductEntity}
     * a una lista iterable de modelos de dominio {@link OrderProduct}.
     *
     * @param orderProductEntities lista de entidades a convertir
     * @return lista iterable de modelos de dominio convertidos
     */
    Iterable<OrderProduct> toOrderProductList(Iterable<OrderProductEntity> orderProductEntities);

    /**
     * Convierte un modelo de dominio {@link OrderProduct} a una entidad {@link OrderProductEntity}.
     * <p>
     * Este método es útil para guardar o actualizar datos en la base de datos.
     *
     * @param orderProduct el modelo de dominio a convertir
     * @return la entidad correspondiente para persistencia
     */
    OrderProductEntity toOrderProductEntity(OrderProduct orderProduct);
}
