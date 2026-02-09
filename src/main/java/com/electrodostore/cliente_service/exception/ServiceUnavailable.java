package com.electrodostore.cliente_service.exception;

import lombok.Getter;

//Excepción personalizada que será usada cuando ocurra algún error técnico o de infraestructura en la comunicación con servicios externos
@Getter  //Exponemos campo(s)
public class ServiceUnavailable extends RuntimeException {

    //ErrorCode que identifica esta excepción fuera del servicio cliente
    private final ClienteErrorCode errorCode;

    //Se sube el mensaje de la excepción para que RuntimeException lo exponga por medio del método getMessage()
    public ServiceUnavailable(String message) {
        super(message);

        //Le asignamos el correspondiente errorCode de la lista de valores en ClienteErrorCode
        this.errorCode = ClienteErrorCode.SERVICE_UNAVAILABLE;
    }
}
