package com.david.foro_alura.exceptions;

public class DuplicadoException extends Exception{
    public DuplicadoException() {
        super();
    }

    public DuplicadoException(String nombreEntidad) {
        super("Error el valor ingresado en el campo " + nombreEntidad + " ya existe");
    }    
}
