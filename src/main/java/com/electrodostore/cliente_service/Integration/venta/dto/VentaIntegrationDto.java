package com.electrodostore.cliente_service.Integration.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transportar los datos de las ventas consultadas.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaIntegrationDto {
    private Long id;
    private LocalDate date;
    private Integer totalItems;
    private BigDecimal totalPrice;
    VentaStatus status;
    private List<ProductoIntegrationDto> productsList = new ArrayList<>();
}
