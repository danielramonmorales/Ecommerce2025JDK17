package com.ecommerce2025.infrastructure.metodoDePago.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que encapsula la lógica de interacción con la API de PayPal.
 * Incluye métodos para crear y ejecutar pagos con PayPal.
 */
@Service
public class PayPalService {

    private final APIContext apiContext;

    /**
     * Constructor que inicializa el servicio con un contexto de API de PayPal.
     *
     * @param apiContext Contexto de la API de PayPal para realizar operaciones
     */
    public PayPalService(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * Crea un pago en PayPal con los parámetros especificados.
     *
     * @param total El monto total del pago.
     * @param currency La moneda en la que se realizará el pago (ej. "USD").
     * @param method El método de pago (ej. "paypal").
     * @param intent El tipo de intención del pago (ej. "sale").
     * @param description La descripción del pago.
     * @param cancelUrl La URL a la que el usuario será redirigido si cancela el pago.
     * @param successUrl La URL a la que el usuario será redirigido si el pago es exitoso.
     * @return El objeto de pago creado.
     * @throws PayPalRESTException Si ocurre un error al interactuar con la API de PayPal.
     */
    public Payment createPayment(Double total, String currency, String method,
                                 String intent, String description, String cancelUrl,
                                 String successUrl) throws PayPalRESTException {

        // Configuración del monto y la moneda
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        // Configuración de la transacción
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Configuración del pagador (usuario que realiza el pago)
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toUpperCase());  // Método de pago en mayúsculas (ej. "PAYPAL")

        // Creación del pago
        Payment payment = new Payment();
        payment.setIntent(intent.toUpperCase());  // Intención del pago (ej. "SALE")
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Configuración de las URL de redirección
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        // Creación y devolución del pago
        return payment.create(apiContext);
    }

    /**
     * Ejecuta un pago previamente creado, utilizando el ID del pago y el ID del pagador.
     *
     * @param paymentId El ID del pago a ejecutar.
     * @param payerId El ID del pagador.
     * @return El objeto de pago ejecutado.
     * @throws PayPalRESTException Si ocurre un error al ejecutar el pago.
     */
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);  // Establece el ID del pago
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);  // Establece el ID del pagador

        // Ejecuta el pago y lo devuelve
        return payment.execute(apiContext, execution);
    }
}
