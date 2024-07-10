package com.ricardotravez.clientepersona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String RECURSO_NO_ENCONTRADO = "003";

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleRecursoNoEncontradoException(RecursoNoEncontradoException exception, WebRequest webRequest) {
        RespuestaError respuestaError = new RespuestaError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), RECURSO_NO_ENCONTRADO);
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

}
