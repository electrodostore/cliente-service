package com.electrodostore.cliente_service.dto;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;//Clase DTO que me va a integrar a los datos de mi cliente con los datos de las ventas que este ha hecho las cuales son consultadas a venta-service

import java.util.List;

public record ClienteConVentasDto(
        List<VentaDto> listVentas,
        ClienteResponseDto cliente
) {}
