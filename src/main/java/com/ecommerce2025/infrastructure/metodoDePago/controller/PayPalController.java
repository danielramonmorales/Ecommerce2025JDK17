package com.ecommerce2025.infrastructure.metodoDePago.controller;

import com.ecommerce2025.infrastructure.metodoDePago.entity.PaymentEntity;
import com.ecommerce2025.infrastructure.metodoDePago.repository.PaymentRepository;
import com.ecommerce2025.infrastructure.metodoDePago.service.PayPalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para manejar pagos con PayPal.
 * Expone endpoints para iniciar, cancelar y completar pagos.
 */
@RestController
@RequestMapping("/api/paypal")
@Tag(name = "PayPal", description = "Endpoints para gestionar pagos con PayPal")
public class PayPalController {

    private final PayPalService payPalService;
    private final PaymentRepository paymentRepository;

    @Value("${server.base.url:http://localhost:8080}")
    private String baseUrl;

    public PayPalController(PayPalService payPalService, PaymentRepository paymentRepository) {
        this.payPalService = payPalService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Inicia un pago con PayPal.
     *
     * @param amount Monto a pagar
     * @return URL de aprobación o error
     */
    @Operation(summary = "Inicia un pago con PayPal")
    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam Double amount) {
        try {
            Payment payment = payPalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    "Pago con PayPal",
                    baseUrl + "/api/paypal/cancel",
                    baseUrl + "/api/paypal/success"
            );

            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.badRequest().body("Error al crear el pago");
    }

    /**
     * Confirma un pago exitoso y lo guarda en la base de datos.
     *
     * @param paymentId ID del pago
     * @param payerId   ID del pagador
     * @param token     Token de sesión
     * @return Detalles del pago completado
     */
    @Operation(summary = "Confirma un pago exitoso")
    @GetMapping("/success")
    public ResponseEntity<?> success(@RequestParam("paymentId") String paymentId,
                                     @RequestParam("PayerID") String payerId,
                                     @RequestParam("token") String token) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            Transaction transaction = payment.getTransactions().get(0);
            Amount amount = transaction.getAmount();

            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setPaymentId(paymentId);
            paymentEntity.setPayerId(payerId);
            paymentEntity.setState(payment.getState());
            paymentEntity.setCurrency(amount.getCurrency());
            paymentEntity.setTotal(amount.getTotal());

            paymentRepository.save(paymentEntity);

            return ResponseEntity.ok(Map.of(
                    "message", "Pago completado y guardado con éxito",
                    "paymentId", payment.getId(),
                    "state", payment.getState(),
                    "token", token,
                    "payerId", payerId
            ));

        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body("Error al ejecutar el pago: " + e.getMessage());
        }
    }

    /**
     * Maneja la cancelación de un pago por parte del usuario.
     *
     * @return Mensaje de cancelación
     */
    @Operation(summary = "Cancelar el proceso de pago")
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel() {
        return ResponseEntity.ok("Pago cancelado por el usuario.");
    }
}
