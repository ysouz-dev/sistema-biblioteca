package com.ysouz.sistemabiblioteca.exception;

public class EmprestimoPendenteException extends RuntimeException {

    public EmprestimoPendenteException(String message) {
        super(message);
    }

    public EmprestimoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
