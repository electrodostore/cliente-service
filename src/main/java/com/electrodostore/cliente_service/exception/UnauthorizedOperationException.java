package com.electrodostore.cliente_service.exception;

import lombok.Getter;

/**
 * Excepción de seguridad para indicar que un
 * usuario no tiene permitido realizar una operación
 */

@Getter
public class UnauthorizedOperationException extends RuntimeException {
    private final ClienteErrorCode errorCode;

    public UnauthorizedOperationException(String message) {
        super(message);

        this.errorCode = ClienteErrorCode.UNAUTHORIZED_OPERATION;
    }
}
