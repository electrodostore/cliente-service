package com.electrodostore.cliente_service.dto;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter  @Setter
@AllArgsConstructor
@NoArgsConstructor
//Clase DTO que me va a integrar a los datos de mi cliente con los datos de las ventas que este ha hecho las cuales son consultadas a venta-service
public class ClienteConVentasDto {

    //Lista de ventas
    private List<VentaDto> listVentas = new ArrayList<>();
    //Datos del cliente
    private ClienteResponseDto cliente;
}
