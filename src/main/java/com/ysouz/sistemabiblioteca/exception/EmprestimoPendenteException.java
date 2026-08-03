package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando o usuário tenta realizar um empréstimo, já possuindo outro
 * empréstimo pendente no momento.
 */
public class EmprestimoPendenteException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public EmprestimoPendenteException(String message) {
        super(message);
    }

    /**
     * Cria a exceção com uma mensagem de erro e a causa original do erro.
     *
     * @param message descrição do erro
     * @param cause exceção original que motivou este erro
     */
    public EmprestimoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
