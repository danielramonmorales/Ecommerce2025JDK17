package com.ecommerce2025.application;

import com.ecommerce2025.domain.model.User;
import com.ecommerce2025.domain.port.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Esta clase prueba el servicio UserService usando JUnit y Mockito.
 * Se enfoca en verificar que el método `save` funcione correctamente.
 */
class UserServiceTest {

    // Simulamos (mockeamos) el repositorio para no depender de una base de datos real.
    private IUserRepository userRepository;
    private UserService userService;

    // Este método se ejecuta antes de cada test. Se usa para preparar el entorno.
    @BeforeEach
    void setUp() {
        // Creamos un mock del repositorio
        userRepository = Mockito.mock(IUserRepository.class);

        // Inyectamos el mock al servicio que vamos a testear
        userService = new UserService(userRepository);
    }

    @Test
    void save_deberiaRetornarUsuarioGuardado() {
        // Arrange (preparación)
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        // Le decimos al mock qué debe hacer: cuando se llame save con este user, que devuelva ese mismo user
        when(userRepository.save(user)).thenReturn(user);

        // Act (acción): llamamos al método que queremos probar
        User result = userService.save(user);

        // Assert (verificación): comprobamos que el resultado sea el esperado
        assertNotNull(result); // Verifica que no sea null
        assertEquals("test@example.com", result.getEmail()); // Verifica que el email sea el correcto

        // Verifica que el método save del repositorio se haya llamado exactamente una vez
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findById_deberiaRetornarUsuarioCuandoExiste() {
        // Arrange: Preparamos el escenario
        User user = new User();                    // Creamos un objeto User vacío
        user.setId(1);                             // Le damos un ID
        user.setEmail("usuario@example.com");      // Y un email

        // Cuando el método findById(1) sea llamado, queremos que devuelva el usuario anterior
        when(userRepository.findById(1)).thenReturn(user);

        // Act: Ejecutamos el método que queremos probar
        User result = userService.findById(1);

        // Assert: Verificamos que el resultado sea correcto
        assertNotNull(result);                           // El resultado no debe ser nulo
        assertEquals(1, result.getId());                 // El ID debe ser 1
        assertEquals("usuario@example.com", result.getEmail()); // El email debe coincidir

        // Verificamos que se llamó al repositorio exactamente una vez con el ID 1
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void findById_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange: Decimos que si se llama a findById con el ID 99, devuelva null (como si no existiera)
        when(userRepository.findById(99)).thenReturn(null);

        // Act & Assert: Ejecutamos el método y esperamos que lance una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findById(99); // Esto debería lanzar una excepción
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Usuario con ID 99 no encontrado.", exception.getMessage());

        // Verificamos que se llamó al método findById con el ID 99 una vez
        verify(userRepository, times(1)).findById(99);
    }

    @Test
    void findByEmail_deberiaRetornarUsuarioCuandoExiste() {
        // Arrange: creamos un usuario simulado
        User user = new User();
        user.setId(2);
        user.setEmail("correo@prueba.com");

        // Configuramos el mock para que cuando se llame a findByEmail con ese correo, devuelva el usuario
        when(userRepository.findByEmail("correo@prueba.com")).thenReturn(user);

        // Act: ejecutamos el método que queremos testear
        User result = userService.findByEmail("correo@prueba.com");

        // Assert: verificamos que el resultado sea correcto
        assertNotNull(result); // El usuario debe existir
        assertEquals("correo@prueba.com", result.getEmail()); // El correo debe coincidir

        // Verificamos que se llamó al método findByEmail con el correo indicado una sola vez
        verify(userRepository, times(1)).findByEmail("correo@prueba.com");
    }

    @Test
    void findByEmail_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange: decimos que el repositorio devuelva null si no encuentra el correo
        when(userRepository.findByEmail("noexiste@correo.com")).thenReturn(null);

        // Act & Assert: ejecutamos y esperamos que lance excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByEmail("noexiste@correo.com");
        });

        // Verificamos el mensaje de la excepción
        assertEquals("Usuario con correo noexiste@correo.com no encontrado.", exception.getMessage());

        // Verificamos que el método fue llamado una vez
        verify(userRepository, times(1)).findByEmail("noexiste@correo.com");
    }




}
