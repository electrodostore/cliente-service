package com.electrodostore.cliente_service.dto;

//Se definene los datos que el cliente (view) puede ver
public record ClienteResponseDto(
        Long id,
        String name,
        String cellphone,
        String document,
        String address,
        boolean active
) {}
