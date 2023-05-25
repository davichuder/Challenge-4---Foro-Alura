package com.david.foro_alura.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.david.foro_alura.dto.error.ValidacionError;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.RespuestaNoCorrespondeException;
import com.david.foro_alura.exceptions.TopicoResueltoException;

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

    @ExceptionHandler(NoExisteException.class)
    public ResponseEntity<Object> entidadNoEncontrada(NoExisteException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<Object> entidadDuplicada(DuplicadoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TopicoResueltoException.class)
    public ResponseEntity<Object> topicoResuelto(TopicoResueltoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RespuestaNoCorrespondeException.class)
    public ResponseEntity<Object> respuestaNoCorresponde(RespuestaNoCorrespondeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}