package com.ecommerce2025.application;


import com.ecommerce2025.domain.model.Order;
import com.ecommerce2025.domain.port.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    // Declaramos las dependencias que vamos a simular (mockear)
    private IOrderRepository orderRepository;
    private OrderService orderService;

    // Este método se ejecuta antes de cada test. Aquí preparamos los mocks.
    @BeforeEach
    void setUp() {
        // Creamos un mock del repositorio
        orderRepository = mock(IOrderRepository.class);
        // Inyectamos el mock en el servicio
        orderService = new OrderService(orderRepository);
    }

    // Test para el método save()
    @Test
    void testSave() {
        // Creamos una orden ficticia
        Order order = new Order(); // Puedes usar setters si tu modelo no usa Lombok

        // Definimos el comportamiento del mock: cuando se llame a save(order), debe devolver la misma orden
        when(orderRepository.save(order)).thenReturn(order);

        // Ejecutamos el método del servicio
        Order result = orderService.save(order);

        // Verificamos que el resultado sea igual a lo esperado
        assertEquals(order, result);

        // Verificamos que el método save() fue invocado una vez con la orden correcta
        verify(orderRepository).save(order);
    }

    // Test para el método findAll()
    @Test
    void testFindAll() {
        // Creamos una lista simulada de órdenes
        List<Order> orders = Arrays.asList(new Order(), new Order());

        // Definimos que cuando se llame a findAll() en el mock, se retorne esa lista
        when(orderRepository.findAll()).thenReturn(orders);

        // Llamamos al método del servicio
        Iterable<Order> result = orderService.findAll();

        // Verificamos que el resultado es el mismo que el simulado
        assertEquals(orders, result);

        // Verificamos que se llamó a findAll() en el repositorio
        verify(orderRepository).findAll();
    }

    // Test para el método findByUserId()
    @Test
    void testFindByUserId() {
        int userId = 1;
        List<Order> orders = Arrays.asList(new Order(), new Order());

        // Simulamos el comportamiento para un ID de usuario
        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        // Ejecutamos el método a probar
        Iterable<Order> result = orderService.findByUserId(userId);

        // Verificamos que el resultado sea el correcto
        assertEquals(orders, result);

        // Verificamos que se llamó al método findByUserId con el ID correcto
        verify(orderRepository).findByUserId(userId);
    }

    // Test para updateStateById()
    @Test
    void testUpdateStateById() {
        int orderId = 10;
        String state = "DELIVERED";

        // No hace falta simular retorno porque el método es void

        // Ejecutamos el método
        orderService.updateStateById(orderId, state);

        // Verificamos que se llamó correctamente al método del mock
        verify(orderRepository).updateStateById(orderId, state);
    }

    // Test para findById()
    @Test
    void testFindById() {
        int orderId = 5;
        Order order = new Order();

        // Simulamos que el repositorio devuelve una orden con ese ID
        when(orderRepository.findById(orderId)).thenReturn(order);

        // Ejecutamos el método del servicio
        Order result = orderService.findById(orderId);

        // Verificamos que la orden devuelta sea la correcta
        assertEquals(order, result);

        // Verificamos que se llamó al método findById del repositorio
        verify(orderRepository).findById(orderId);
    }
    // ...

    // Test negativo: findById cuando no encuentra nada (retorna null)
    @Test
    void testFindById_NotFound() {
        int orderId = 99;

        // Simulamos que el repositorio devuelve null para un ID que no existe
        when(orderRepository.findById(orderId)).thenReturn(null);

        // Ejecutamos el método
        Order result = orderService.findById(orderId);

        // Verificamos que el resultado sea null
        assertNull(result);

        // Verificamos que se llamó correctamente
        verify(orderRepository).findById(orderId);
    }

    // Test negativo: findByUserId con lista vacía
    @Test
    void testFindByUserId_EmptyResult() {
        int userId = 2;

        // Simulamos una lista vacía de órdenes
        when(orderRepository.findByUserId(userId)).thenReturn(List.of());

        // Llamamos al método
        Iterable<Order> result = orderService.findByUserId(userId);

        // Verificamos que el resultado no sea null, pero esté vacío
        assertNotNull(result);
        assertFalse(result.iterator().hasNext());

        // Verificamos la llamada
        verify(orderRepository).findByUserId(userId);
    }

    // Test negativo: findAll sin órdenes
    @Test
    void testFindAll_EmptyResult() {
        // Repositorio devuelve una lista vacía
        when(orderRepository.findAll()).thenReturn(List.of());

        Iterable<Order> result = orderService.findAll();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());

        verify(orderRepository).findAll();
    }

    // Test negativo: updateStateById con ID inexistente (simulado como éxito silencioso)
    @Test
    void testUpdateStateById_NonExistingId() {
        int fakeId = 999;
        String newState = "CANCELLED";

        // No hacemos when(...) ya que es void y no lanza excepción

        // Ejecutamos igualmente el método
        orderService.updateStateById(fakeId, newState);

        // Aun así, debe haberse llamado con los argumentos correctos
        verify(orderRepository).updateStateById(fakeId, newState);
    }


}

