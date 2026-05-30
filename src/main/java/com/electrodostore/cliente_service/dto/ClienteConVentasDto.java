package com.electrodostore.cliente_service.dto;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaIntegrationDto;//Clase DTO que me va a integrar a los datos de mi cliente con los datos de las ventas que este ha hecho las cuales son consultadas a venta-service

import java.util.List;

/**
 * Transporta los datos de un cliente con sus ventas
 * consultadas en venta-service
 */
public record ClienteConVentasDto(
        List<VentaIntegrationDto> listVentas,
        ClienteResponseDto cliente
) {}
