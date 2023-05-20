package com.david.foro_alura.dto.error;

import org.springframework.validation.FieldError;

public  record ErrorValidacion(String campo, String error){
    public ErrorValidacion(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}