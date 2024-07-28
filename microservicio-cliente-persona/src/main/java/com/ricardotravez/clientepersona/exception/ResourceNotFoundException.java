package com.ricardotravez.clientepersona.exception;

public class ResourceNotFoundException extends RuntimeException{
    private String mensaje;

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
}
