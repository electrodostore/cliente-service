package com.electrodostore.cliente_service.dto;

import jakarta.validation.constraints.Pattern;

/**
 * DTO para actualizar parcialmente
 * los datos de un cliente.
 * */
public record ClientePatchRequestDto(
        String name,
        String cellphone,
        @Pattern(regexp = "\\d+") String document,
        String address
) {
}
