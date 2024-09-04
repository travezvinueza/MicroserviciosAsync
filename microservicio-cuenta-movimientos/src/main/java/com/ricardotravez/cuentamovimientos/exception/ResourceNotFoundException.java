package com.ricardotravez.cuentamovimientos.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
