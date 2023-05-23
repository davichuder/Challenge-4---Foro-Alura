package com.david.foro_alura.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.david.foro_alura.dto.error.ValidacionError;
import com.david.foro_alura.exceptions.ExisteException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> recursoNoEncontrado(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(ValidacionError::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ExisteException.class)
    public ResponseEntity<Object> entidadNoEncontrada(ExisteException e) {
        var errores = e.getMessage();
        return ResponseEntity.badRequest().body(errores);
    }
}