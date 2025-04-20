package com.ecommerce2025.infrastructure.mapper;

import com.ecommerce2025.domain.model.Category; // Importa la clase de dominio Category
import com.ecommerce2025.infrastructure.dto.entity.CategoryEntity; // Importa la clase de entidad CategoryEntity
import org.mapstruct.InheritInverseConfiguration; // Anotación para generar automáticamente el mapeo inverso
import org.mapstruct.Mapper; // Anotación de MapStruct para crear el mapper
import org.mapstruct.Mapping; // Anotación de MapStruct para mapear campos específicos
import org.mapstruct.Mappings; // Anotación que permite la agrupación de múltiples mapeos

@Mapper(componentModel = "spring") // Se define como un componente Spring para la inyección de dependencias
public interface CategoryMapper {

    // Método para convertir una entidad CategoryEntity a un objeto de dominio Category
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"), // Mapea el campo 'id' de CategoryEntity a 'id' de Category
                    @Mapping(source = "name", target = "name"), // Mapea el campo 'name' de CategoryEntity a 'name' de Category
                    @Mapping(source = "description", target = "description"), // Mapea el campo 'description' de CategoryEntity a 'description' de Category
                    @Mapping(source = "dateCreated", target = "dateCreated"), // Mapea el campo 'dateCreated' de CategoryEntity a 'dateCreated' de Category
                    @Mapping(source = "dateUpdated", target = "dateUpdated") // Mapea el campo 'dateUpdated' de CategoryEntity a 'dateUpdated' de Category
            }
    )
    Category toCategory(CategoryEntity categoryEntity); // Convierte una instancia de CategoryEntity a Category

    // Método para convertir una lista de CategoryEntity a una lista de Category
    Iterable<Category> toCategoryList(Iterable<CategoryEntity> categoryEntities);

    // Método para convertir una instancia de Category a CategoryEntity (mapeo inverso)
    @InheritInverseConfiguration // Instrucción para MapStruct: genera el mapeo inverso utilizando la configuración definida en 'toCategory'
    CategoryEntity toCategoryEntity(Category category); // Convierte una instancia de Category a CategoryEntity
}
