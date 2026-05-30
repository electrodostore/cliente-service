package com.electrodostore.cliente_service.Integration.venta;

import com.electrodostore.cliente_service.Integration.venta.client.VentaFeignClient;
import com.electrodostore.cliente_service.Integration.venta.dto.VentaIntegrationDto;
import com.electrodostore.cliente_service.exception.ServiceUnavailable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase de integración con venta-service que protege
 * integraciones entre ambos servicios usando
 * Circuit Breaker y Retry
 * */
@Slf4j
@Service
public class VentaIntegrationService {

    private final VentaFeignClient ventaClient;

    public VentaIntegrationService(VentaFeignClient ventaClient) {
        this.ventaClient = ventaClient;
    }

    /**
     * Consulta a venta-service de las ventas
     * asociadas a un cliente.
     * */
    @CircuitBreaker(name = "venta-service", fallbackMethod = "findClienteVentasFallback")
    @Retry(name = "venta-service")
    public List<VentaIntegrationDto> findClienteVentas(Long clienteId) {
        return ventaClient.findClienteVentas(clienteId);
    }

    /**
     * Fallback activado cuando ocurre un error de integración
     * al consultar las ventas de un cliente.
     * */
    public List<VentaIntegrationDto> findClienteVentasFallback(Long clienteId, Throwable ex){

        /**
         * Informa el error de infraestructura en la integración.
         */
        log.warn("fallback activado para comunicación con venta-service", ex);
        throw new ServiceUnavailable("No se pudo establecer comunicación con venta-service. Intente de nuevo más tarde");
    }


}
