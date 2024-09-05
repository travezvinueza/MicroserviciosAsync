package com.ricardotravez.clientepersona.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
