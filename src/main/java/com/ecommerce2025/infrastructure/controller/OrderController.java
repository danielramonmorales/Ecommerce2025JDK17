package com.ecommerce2025.infrastructure.controller;

import com.ecommerce2025.application.OrderService;
import com.ecommerce2025.domain.model.Order;
import com.ecommerce2025.domain.model.OrderState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Guarda una nueva orden en el sistema. El estado de la orden será
     * CANCELLED si el estado recibido es CANCELLED, o CONFIRMED si es otro.
     *
     * @param order el objeto Order a guardar
     * @return el objeto Order guardado en la base de datos
     */
    @Operation(summary = "Guardar una nueva orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden guardada correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición inválida")
    })
    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order){
        if(order.getOrderState().toString().equals(OrderState.CANCELLED.toString())){
            order.setOrderState(OrderState.CANCELLED);
        }else{
            order.setOrderState(OrderState.CONFIRMED);
        }
        return ResponseEntity.ok(orderService.save(order));
    }

    /**
     * Actualiza el estado de una orden dado su ID.
     *
     * @param id el ID de la orden
     * @param state el nuevo estado de la orden
     * @return una respuesta vacía con el código de estado HTTP 200
     */
    @Operation(summary = "Actualizar el estado de una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la orden actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PostMapping("/update/state/order")
    public ResponseEntity updateStateById(@RequestParam Integer id, @RequestParam String state){
        orderService.updateStateById(id, state);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene todas las órdenes del sistema.
     *
     * @return una lista de todas las órdenes
     */
    @Operation(summary = "Obtener todas las órdenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<Iterable<Order>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

    /**
     * Obtiene una orden específica por su ID.
     *
     * @param id el ID de la orden
     * @return la orden correspondiente al ID proporcionado
     */
    @Operation(summary = "Obtener una orden por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("{variable}")
    public ResponseEntity<Order> findById(@PathVariable("variable") Integer id){
        return  ResponseEntity.ok(orderService.findById(id));
    }

    /**
     * Obtiene todas las órdenes asociadas a un usuario dado su ID.
     *
     * @param userId el ID del usuario
     * @return una lista de órdenes asociadas al usuario
     */
    @Operation(summary = "Obtener órdenes por ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Order>> findByUserId(@PathVariable("id") Integer userId){
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }
}
