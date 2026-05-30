package com.electrodostore.cliente_service.exception;

import lombok.Getter;

@Getter
public class ClienteNotFoundException extends RuntimeException{
    private final ClienteErrorCode errorCode;

    public ClienteNotFoundException(String message){
        super(message);
        this.errorCode = ClienteErrorCode.CLIENT_NOT_FOUND;
    }
}
