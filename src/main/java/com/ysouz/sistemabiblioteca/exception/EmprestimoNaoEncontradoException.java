package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando um empréstimo não é encontrado no sistema.
 */
public class EmprestimoNaoEncontradoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }

    /**
     * Cria a exceção com uma mensagem de erro e a causa original do erro
     *
     * @param message descrição do erro
     * @param cause exceção original que motivou este erro
     */
    public EmprestimoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
