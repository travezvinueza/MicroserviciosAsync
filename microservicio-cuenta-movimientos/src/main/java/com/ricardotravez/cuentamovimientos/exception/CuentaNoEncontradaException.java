package com.ricardotravez.cuentamovimientos.exception;

public class CuentaNoEncontradaException extends RuntimeException{
    private String mensaje;

    public CuentaNoEncontradaException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}
