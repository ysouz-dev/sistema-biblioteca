package com.ysouz.sistemabiblioteca.exception;

public class LivroJaEmprestadoException extends RuntimeException {
    public LivroJaEmprestadoException(String message) {
        super(message);
    }

    public LivroJaEmprestadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
