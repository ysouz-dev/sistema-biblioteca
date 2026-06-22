package com.ysouz.sistemabiblioteca.exception;

public class LivroJaCadastradoException extends RuntimeException {
    public LivroJaCadastradoException(String message) {
        super(message);
    }

    public LivroJaCadastradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
