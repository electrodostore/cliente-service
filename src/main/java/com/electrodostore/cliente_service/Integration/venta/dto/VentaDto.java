package com.electrodostore.cliente_service.Integration.venta.dto;

import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
//Clase DTO donde se van a almacenar los datos de cada venta una vez sean deserealizados
public class VentaDto {
    private LocalDate date;
    private Integer totalItems;
    private BigDecimal totalPrice;
    //Lista de productos
    private List<ProductoDto> productsList = new ArrayList<>();
}
