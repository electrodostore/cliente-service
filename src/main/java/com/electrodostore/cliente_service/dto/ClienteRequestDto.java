package com.electrodostore.cliente_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDto {

    private String name;
    private String cellphone;
    private String document;
    private String address;
}
