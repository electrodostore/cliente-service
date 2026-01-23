package com.electrodostore.cliente_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@AllArgsConstructor
@NoArgsConstructor
//Se definene los datos que el cliente (view) puede ver
public class ClienteResponseDto {

    private Long id;
    private String name;
    private String cellphone;
    private String document;
    private String address;
}
