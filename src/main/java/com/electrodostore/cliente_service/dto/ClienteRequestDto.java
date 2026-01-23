package com.electrodostore.cliente_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@AllArgsConstructor
@NoArgsConstructor
//Solo se definen datos que el cliente (view) puede ingresar o actualizar
public class ClienteRequestDto {

    private String name;
    private String cellphone;
    private String document;
    private String address;
}
