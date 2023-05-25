package com.david.foro_alura.exceptions;

public class TopicoResueltoException extends Exception{
    public TopicoResueltoException() {
        super();
    }

    public TopicoResueltoException(Long id) {
        super("Error: el topico " + id + " ya esta resuelto");
    }    
}
