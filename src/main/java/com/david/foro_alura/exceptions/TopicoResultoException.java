package com.david.foro_alura.exceptions;

public class TopicoResultoException extends Exception{
    public TopicoResultoException() {
        super();
    }

    public TopicoResultoException(Long id) {
        super("Error: el topico " + id + " ya esta resulta");
    }    
}
