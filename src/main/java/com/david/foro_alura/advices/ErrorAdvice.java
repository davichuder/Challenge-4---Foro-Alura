package com.david.foro_alura.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.david.foro_alura.dto.error.ValidacionError;
import com.david.foro_alura.enums.Estatus;
import com.david.foro_alura.enums.Rol;
import com.david.foro_alura.exceptions.DuplicadoException;
import com.david.foro_alura.exceptions.NoEsCreadorException;
import com.david.foro_alura.exceptions.NoExisteException;
import com.david.foro_alura.exceptions.RespuestaNoCorrespondeException;
import com.david.foro_alura.exceptions.TopicoResueltoException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> recursoNoEncontrado(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(ValidacionError::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoExisteException.class)
    public ResponseEntity<Object> entidadNoEncontrada(NoExisteException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<Object> entidadDuplicada(DuplicadoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TopicoResueltoException.class)
    public ResponseEntity<Object> topicoResuelto(TopicoResueltoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RespuestaNoCorrespondeException.class)
    public ResponseEntity<Object> respuestaNoCorresponde(RespuestaNoCorrespondeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NoEsCreadorException.class)
    public ResponseEntity<Object> noEsCreador(NoEsCreadorException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> argumentoInvalido(IllegalArgumentException e) {
        String mensaje = e.getMessage();
        if (e.getMessage().contains("enums.Estatus")) {
            mensaje = "Los valores valido para estatus son:";
            for (Estatus estatus : Estatus.values()) {
                mensaje = mensaje + "\t" + estatus.toString();
            }
        }
        return ResponseEntity.badRequest().body(mensaje);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> enumRolIncorrecto(HttpMessageNotReadableException e) {
        String mensaje = e.getMessage();
        if (e.getMessage().contains("enums.Rol")) {
            mensaje = "Los valores valido para rol son:";
            for (Rol estatus : Rol.values()) {
                mensaje = mensaje + "\t\t" + estatus.toString();
            }
        }
        return ResponseEntity.badRequest().body(mensaje);
    }
}