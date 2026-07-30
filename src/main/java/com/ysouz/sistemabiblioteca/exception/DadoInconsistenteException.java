package com.ysouz.sistemabiblioteca.exception;

public class DadoInconsistenteException extends RuntimeException {
    public DadoInconsistenteException(String message) {
        super(message);
    }

    public DadoInconsistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
