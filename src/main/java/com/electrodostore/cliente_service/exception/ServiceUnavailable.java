package com.electrodostore.cliente_service.exception;

import lombok.Getter;

@Getter
public class ServiceUnavailable extends RuntimeException {
    private final ClienteErrorCode errorCode;

    public ServiceUnavailable(String message) {
        super(message);
        this.errorCode = ClienteErrorCode.SERVICE_UNAVAILABLE;
    }
}
