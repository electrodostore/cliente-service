package com.electrodostore.cliente_service.Integration.venta;

import com.electrodostore.cliente_service.Integration.venta.client.VentaFeignClient;
import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import com.electrodostore.cliente_service.exception.ServiceUnavailable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/*Clase de integración con venta-service cuya función es llamar al cliente del servicio Venta y hacer la integración de las
 diferentes consultas a ese servicio con este service*/
@Slf4j  //@Slf4j tiene un logger para agregar mensajes de warning o error al log del proyecto
@Service
public class VentaIntegrationService {

    //Inyección de dependencia por constructor para la clase que es cliente del servicio Venta
    private final VentaFeignClient ventaClient;
    public VentaIntegrationService(VentaFeignClient ventaClient) {
        this.ventaClient = ventaClient;
    }

    //Método protegido por Circuit-Breaker cuya función es consultar al servicio Venta y traer la lista de ventas de un cliente por su id
    @CircuitBreaker(name = "venta-service", fallbackMethod = "findClienteVentasFallback")
    @Retry(name = "venta-service")
    public List<VentaDto> findClienteVentas(Long clienteId) {
        return ventaClient.findClienteVentas(clienteId);
    }

    //Fallback que sirve como planB en la consulta en caso de que haya algún problema técnico en la comunicación
    //El método fallBack debe tener la misma firma que el método protegido con un parámetro adicional Throwable que es la excepción que lo activó
    public List<VentaDto> findClienteVentasFallback(Long clienteId, Throwable ex){

        //Agregamos log al proyecto indicando la activación del fallback
        log.warn("fallback activado para comunicación con venta-service", ex);

        //Lanzamos excepción de infraestructura comunicando el problema
        throw new ServiceUnavailable("No se pudo establecer comunicación con venta-service. Intente de nuevo más tarde");
    }


}
