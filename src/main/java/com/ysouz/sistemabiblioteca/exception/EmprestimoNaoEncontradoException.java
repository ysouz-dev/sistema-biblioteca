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
}
