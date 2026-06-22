package com.ysouz.sistemabiblioteca.exception;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String message) {
        super(message);
    }

    public LivroNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

}
