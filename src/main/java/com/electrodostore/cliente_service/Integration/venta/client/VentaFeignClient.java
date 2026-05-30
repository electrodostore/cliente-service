package com.electrodostore.cliente_service.Integration.venta.client;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaIntegrationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Cliente Feign que realiza peticiones a
 * venta-service
 */
@FeignClient(name = "venta-service") //Mismo nombre de registro en eureka-server
public interface VentaFeignClient {

    //Consulta ventas asociadas a un cliente.
    @GetMapping("/ventas/cliente/{clientId}")
    List<VentaIntegrationDto> findClienteVentas(@PathVariable Long clientId);
}
