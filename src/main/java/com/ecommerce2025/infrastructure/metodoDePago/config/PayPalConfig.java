package com.ecommerce2025.infrastructure.metodoDePago.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración de PayPal para integración con la API REST.
 * Define beans necesarios para autenticación y contexto de ejecución.
 */
@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    /**
     * Configuración del SDK de PayPal, define el modo (sandbox/live).
     *
     * @return Mapa de configuración
     */
    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("mode", mode);
        return config;
    }

    /**
     * Bean para manejar el token OAuth de PayPal.
     *
     * @return OAuthTokenCredential con clientId y clientSecret
     */
    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    /**
     * Contexto principal de la API de PayPal con el token de acceso.
     *
     * @return APIContext configurado
     * @throws PayPalRESTException si falla la obtención del token
     */
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(authTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
