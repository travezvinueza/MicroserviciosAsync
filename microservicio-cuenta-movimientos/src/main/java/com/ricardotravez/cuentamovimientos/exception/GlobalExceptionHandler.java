package com.ricardotravez.cuentamovimientos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String SALDO_NO_DISPONIBLE = "001";
    private final String CUENTA_NO_ENCONTRADA = "002";
    private final String RECURSO_NO_ENCONTRADO = "003";

    private final String CLIENTE_NO_ENCONTRADO = "004";

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<RespuestaError> handleSaldoInsuficienteException(SaldoInsuficienteException exception, WebRequest webRequest) {
        RespuestaError respuestaError = new RespuestaError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), SALDO_NO_DISPONIBLE);
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CuentaNoEncontradaException.class)
    public ResponseEntity<RespuestaError> handleCuentaNoEncontradaException(CuentaNoEncontradaException exception, WebRequest webRequest) {
        RespuestaError respuestaError = new RespuestaError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), CUENTA_NO_ENCONTRADA);
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleRecursoNoEncontradoException(RecursoNoEncontradoException exception, WebRequest webRequest) {
        RespuestaError respuestaError = new RespuestaError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), RECURSO_NO_ENCONTRADO);
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleClienteNoEncontradoException(ClienteNoEncontradoException exception, WebRequest webRequest) {
        RespuestaError respuestaError = new RespuestaError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), CLIENTE_NO_ENCONTRADO);
        return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
    }

}
