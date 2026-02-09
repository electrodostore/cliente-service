package com.electrodostore.cliente_service.Integration.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter  @Setter
@AllArgsConstructor
@NoArgsConstructor
/*Clase DTO que almacena los datos de los productos que puedan estar en una venta comprada por un determinado
 cliente */
public class ProductoDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer purchasedQuantity;
    private BigDecimal subTotal;
    private String description;
}
