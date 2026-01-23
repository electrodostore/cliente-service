package com.electrodostore.cliente_service.exception;

//Clase representativa de la exceoción personalizada que se usará
// cuando no se logre encontrar a un determinado Cliente.
public class ClienteNotFoundException extends RuntimeException{

    /*Se envía el mensaje a la superclase para que se encargue de ponerlo a nuestra disposición por medio
    del método getMessage()*/
    public ClienteNotFoundException(String message){
        super(message);
    }
}
