package com.ysouz.sistemabiblioteca.exception;

public class EmprestimoNaoEncontradoException extends RuntimeException {

    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }

    public EmprestimoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
