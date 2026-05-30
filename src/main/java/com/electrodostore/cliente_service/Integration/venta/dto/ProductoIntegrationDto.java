package com.electrodostore.cliente_service.Integration.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO usado para transportar los datos de los productos
 * dentro de las ventas consultadas en venta-service.
 */
@Getter  @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoIntegrationDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer purchasedQuantity;
    private BigDecimal subTotal;
    private String description;
}
