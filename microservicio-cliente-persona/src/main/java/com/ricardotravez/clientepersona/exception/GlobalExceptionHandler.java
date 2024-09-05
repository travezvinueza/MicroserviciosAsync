package com.ricardotravez.clientepersona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String RECURSO_NO_ENCONTRADO = "003";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleRecursoNoEncontradoException(ResourceNotFoundException exception, WebRequest webRequest) {
        ResponseError responseError = new ResponseError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), RECURSO_NO_ENCONTRADO);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

}
