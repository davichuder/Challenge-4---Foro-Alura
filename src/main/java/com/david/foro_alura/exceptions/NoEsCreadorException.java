package com.david.foro_alura.exceptions;

public class NoEsCreadorException extends Exception{
    public NoEsCreadorException() {
        super("Usted no es el creador de este topico");
    }
}
