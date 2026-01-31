package com.electrodostore.cliente_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/*Manejador global de excepciones, hace que cuando se registre una excepción
Spring revise esta clase para ver si encuentra un manejador que manje la excepción*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Método propio para construir el mensaje de error de una determinada excepción
    private Map<String, Object> buildErrorResponse(HttpStatus status, String message, String errorCode){
        Map<String, Object> response = new LinkedHashMap<>();

        //Definimos pares clave-valor para la response
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("errorCode", errorCode); //errorCode identificativo de cada excepción
        response.put("mensaje", message);

        return response;
    }

    /*Manejador de la excepción ClienteNotFound hace que si la excepción
    es por un cliente no encontrado, me tome esta response completa y me la envie a la vista*/
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerClienteNotFound(ClienteNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getErrorCode().name()));
    }
}
