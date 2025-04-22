package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.Order;
import com.ecommerce2025.domain.model.OrderProduct;
import com.ecommerce2025.domain.model.Product;
import com.ecommerce2025.domain.port.IOrderRepository;
import com.ecommerce2025.domain.port.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación encargado de gestionar operaciones relacionadas con las órdenes.
 * Actúa como intermediario entre los controladores y el repositorio de órdenes.
 */
@Service
public class OrderService {

    /** Repositorio que permite acceder y manipular datos de órdenes */
    private final IOrderRepository iOrderRepository;

    private final IProductRepository iProductRepository;



    /**
     * Constructor que inyecta una implementación del repositorio de órdenes.
     *
     * @param iOrderRepository Repositorio de órdenes a utilizar
     */
    @Autowired
    public OrderService(IOrderRepository iOrderRepository, IProductRepository iProductRepository) {
        this.iOrderRepository = iOrderRepository;
        this.iProductRepository = iProductRepository;
    }

    // ======> MÉTODOS: lógica de negocio para operaciones sobre órdenes

    /**
     * Guarda una nueva orden en el sistema.
     *
     * @param order La orden a guardar
     * @return La orden guardada con su ID generado
     */
    public Order save(Order order) {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            // Obtenemos el producto desde la base de datos usando su productId
            Product product = iProductRepository.findById(orderProduct.getProductId());

            if (product == null) {
                // Si no se encuentra el producto, lanzamos una excepción
                throw new RuntimeException("Producto con ID " + orderProduct.getProductId() + " no encontrado.");
            }

            // Actualizamos el precio en la orden con el precio real del producto
            orderProduct.setPrice(product.getPrice());
        }

        // Guardamos la orden con los precios actualizados
        return this.iOrderRepository.save(order);
    }


    /**
     * Obtiene todas las órdenes existentes.
     *
     * @return Iterable con todas las órdenes
     */
    public Iterable<Order> findAll() {
        return this.iOrderRepository.findAll();
    }

    /**
     * Busca todas las órdenes asociadas a un usuario específico.
     *
     * @param userId ID del usuario
     * @return Iterable con las órdenes del usuario
     */
    public Iterable<Order> findByUserId(Integer userId) {
        return this.iOrderRepository.findByUserId(userId);
    }

    /**
     * Actualiza el estado de una orden específica por su ID.
     *
     * @param id IDde la orden
     * @param state Nuevo estado de la orden
     */
    public void updateStateById(Integer id, String state) {
        this.iOrderRepository.updateStateById(id, state);
    }

    /**
     * Busca una orden específica por su ID.
     *
     * @param id IDde la orden
     * @return La orden encontrada, o null si no existe
     */
    public Order findById(Integer id) {
        return this.iOrderRepository.findById(id);
    }
}
