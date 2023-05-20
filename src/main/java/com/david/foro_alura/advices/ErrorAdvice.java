package com.david.foro_alura.advices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.david.foro_alura.dto.error.ErrorValidacion;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(ErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
}