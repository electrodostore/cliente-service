package com.electrodostore.cliente_service.dto;

//Solo se definen datos que el cliente (view) puede ingresar o actualizar
public record ClienteRequestDto(
        String name,
        String cellphone,
        String document,
        String address
) {}
