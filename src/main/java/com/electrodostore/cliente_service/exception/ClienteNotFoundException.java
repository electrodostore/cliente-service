package com.electrodostore.cliente_service.exception;

import lombok.Getter;

@Getter  //->Exposición de campo(s)}

//Clase representativa de la excepción personalizada que se usará
// cuando no se logre encontrar a un determinado Cliente.
public class ClienteNotFoundException extends RuntimeException{

    //ErrorCode que identifica esta excepción fuera del dominio de este servicio Cliente
    private final ClienteErrorCode errorCode;

    /*Se envía el mensaje a la superclase para que se encargue de ponerlo a nuestra disposición por medio
    del método getMessage()*/
    public ClienteNotFoundException(String message){
        super(message);

        //Se le asigna el valor del code correspondiente en el enum ClienteErrorCode
        this.errorCode = ClienteErrorCode.CLIENT_NOT_FOUND;
    }
}
