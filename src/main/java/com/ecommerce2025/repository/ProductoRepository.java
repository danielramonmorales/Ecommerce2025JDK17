package com.ecommerce2025.repository;

import com.ecommerce2025.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Podés agregar métodos personalizados si querés
}
