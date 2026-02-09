package com.electrodostore.cliente_service.Integration.venta.client;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Clase cliente de venta-service donde se describirán cada uno de los end-points para las diferentes consultas
@FeignClient(name = "venta-service") //Mismo nombre de registro en eureka-server
public interface VentaFeignClient {

    /*Descripción del método de venta-service que se encarga de buscar y traer la lista de ventas de un determinado
      cliente por su id */
    @GetMapping("/ventas/traer-ventas-de-cliente/{clientId}")
    List<VentaDto> findClienteVentas(@PathVariable Long clientId);
}
