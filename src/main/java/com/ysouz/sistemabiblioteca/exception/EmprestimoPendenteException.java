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
}
