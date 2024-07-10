package com.ricardotravez.cuentamovimientos.exception;

public class ClienteNoEncontradoException extends RuntimeException{
    private String mensaje;

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}
