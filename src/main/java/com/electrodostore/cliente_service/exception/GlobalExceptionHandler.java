package com.electrodostore.cliente_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Centraliza el manejo de excepciones conocidas y envía
 * una respuesta personalizada del error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Construye el cuerpo de la respuesta
     * para las excepciones personalizadas.
     */
    private Map<String, Object> buildErrorResponse(HttpStatus status, String message, String errorCode){
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("errorCode", errorCode); //errorCode identificativo de cada excepción
        response.put("mensaje", message);

        return response;
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerClienteNotFound(ClienteNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getErrorCode().name()));
    }

    @ExceptionHandler(ServiceUnavailable.class)
    public ResponseEntity<Map<String, Object>> handlerServiceUnavailable(ServiceUnavailable ex){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), ex.getErrorCode().name()));
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<Map<String, Object>> handlerUnauthorizedOperation(UnauthorizedOperationException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), ex.getErrorCode().name()));
    }

}
