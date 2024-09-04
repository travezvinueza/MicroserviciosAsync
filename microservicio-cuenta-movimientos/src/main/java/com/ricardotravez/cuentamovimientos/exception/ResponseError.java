package com.ricardotravez.cuentamovimientos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseError {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String code;
}
