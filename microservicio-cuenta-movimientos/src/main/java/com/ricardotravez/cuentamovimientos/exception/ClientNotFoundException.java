package com.ricardotravez.cuentamovimientos.exception;

public class ClientNotFoundException extends RuntimeException{
    private String message;

    public ClientNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
