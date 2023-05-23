package com.david.foro_alura.exceptions;

public class ExisteException extends Exception{
    public ExisteException() {
        super();
    }

    public ExisteException(String nombreEntidad) {
        super("Error el ID del " + nombreEntidad + " no esta existe");
    }    
}
