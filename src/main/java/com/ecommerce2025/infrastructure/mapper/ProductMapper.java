package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.Product; // Importa la clase de dominio Product
import com.ecommerce2025.infrastructure.dto.entity.ProductEntity; // Importa la clase de entidad ProductEntity
import org.mapstruct.InheritInverseConfiguration; // Anotación para generar automáticamente el mapeo inverso
import org.mapstruct.Mapper; // Anotación de MapStruct para crear el mapper
import org.mapstruct.Mapping; // Anotación de MapStruct para mapear campos específicos
import org.mapstruct.Mappings; // Anotación que permite la agrupación de múltiples mapeos

// La anotación @Mapper indica que esta interfaz es un Mapper que MapStruct va a procesar para generar la implementación.
// El atributo componentModel = "spring" permite que MapStruct registre la clase generada como un componente Spring,
// de modo que se pueda inyectar como un servicio en cualquier parte de la aplicación.
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Convierte una instancia de ProductEntity (entidad JPA) a Product (modelo de dominio).
     *
     * @param productEntity La entidad ProductEntity que se va a convertir.
     * @return El modelo de dominio Product.
     */
    @Mappings(
            {
                    // Mapea el campo 'id' de ProductEntity a 'id' de Product
                    @Mapping(source = "id", target = "id"),
                    // Mapea el campo 'name' de ProductEntity a 'name' de Product
                    @Mapping(source = "name", target = "name"),
                    // Mapea el campo 'code' de ProductEntity a 'code' de Product
                    @Mapping(source = "code", target = "code"),
                    // Mapea el campo 'description' de ProductEntity a 'description' de Product
                    @Mapping(source = "description", target = "description"),
                    // Mapea el campo 'urlImage' de ProductEntity a 'urlImage' de Product
                    @Mapping(source = "urlImage", target = "urlImage"),
                    @Mapping(source = "imagePublicId", target = "imagePublicId"),
                    // Mapea el campo 'price' de ProductEntity a 'price' de Product
                    @Mapping(source = "price", target = "price"),
                    // Mapea el campo 'dateCreated' de ProductEntity a 'dateCreated' de Product
                    @Mapping(source = "dateCreated", target = "dateCreated"),
                    // Mapea el campo 'dateUpdated' de ProductEntity a 'dateUpdated' de Product
                    @Mapping(source = "dateUpdated", target = "dateUpdated"),
                    // Mapea el campo 'id' de la entidad userEntity a 'userId' de Product
                    @Mapping(source = "userEntity.id", target = "userId"),
                    // Mapea el campo 'id' de la entidad categoryEntity a 'categoryId' de Product
                    @Mapping(source = "categoryEntity.id", target = "categoryId")
            }
    )
    Product toProduct(ProductEntity productEntity); // Convierte una instancia de ProductEntity a Product

    /**
     * Convierte una lista de ProductEntity a una lista de Product.
     *
     * @param productEntities El iterable de ProductEntity que se va a convertir.
     * @return Una lista de Product.
     */
    Iterable<Product> toProductList(Iterable<ProductEntity> productEntities);

    /**
     * Convierte una instancia de Product (modelo de dominio) a ProductEntity (entidad JPA).
     *
     * @param product El modelo de dominio Product que se va a convertir.
     * @return La entidad ProductEntity.
     */
    @InheritInverseConfiguration // Esta anotación genera automáticamente el mapeo inverso utilizando la configuración del método 'toProduct'.
    ProductEntity toProductEntity(Product product); // Convierte una instancia de Product a ProductEntity
}
