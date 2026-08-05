package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando um livro não é encontrado no sistema.
 */
public class LivroNaoEncontradoException extends RuntimeException {
    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public LivroNaoEncontradoException(String message) {
        super(message);
    }
}
