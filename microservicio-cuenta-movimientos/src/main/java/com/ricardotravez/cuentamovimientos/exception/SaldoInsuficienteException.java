package com.ricardotravez.cuentamovimientos.exception;

public class SaldoInsuficienteException extends RuntimeException{

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }

}
