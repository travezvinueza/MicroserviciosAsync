package com.ricardotravez.clientepersona.exception;

public class RecursoNoEncontradoException extends RuntimeException{
    private String mensaje;

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}
