package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando um usuário não é encontrado no sistema.
 */
public class UsuarioNaoEncontradoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
