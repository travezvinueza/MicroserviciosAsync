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
    public ResponseEntity<ResponseError> handleSaldoInsuficienteException(SaldoInsuficienteException exception, WebRequest webRequest) {
        ResponseError responseError = new ResponseError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), SALDO_NO_DISPONIBLE);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseError> handleCuentaNoEncontradaException(AccountNotFoundException exception, WebRequest webRequest) {
        ResponseError responseError = new ResponseError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), CUENTA_NO_ENCONTRADA);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleRecursoNoEncontradoException(ResourceNotFoundException exception, WebRequest webRequest) {
        ResponseError responseError = new ResponseError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), RECURSO_NO_ENCONTRADO);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ResponseError> handleClienteNoEncontradoException(ClientNotFoundException exception, WebRequest webRequest) {
        ResponseError responseError = new ResponseError(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), CLIENTE_NO_ENCONTRADO);
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

}
