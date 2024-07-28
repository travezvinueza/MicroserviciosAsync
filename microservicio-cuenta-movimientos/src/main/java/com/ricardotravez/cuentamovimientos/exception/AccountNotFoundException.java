package com.ricardotravez.cuentamovimientos.exception;

public class AccountNotFoundException extends RuntimeException{
    private String message;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
