package com.ecommerce2025.mapper;

import com.ecommerce2025.dto.ProductoDTO;
import com.ecommerce2025.model.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDTO toDto(Producto producto);
    Producto toEntity(ProductoDTO dto);
}
