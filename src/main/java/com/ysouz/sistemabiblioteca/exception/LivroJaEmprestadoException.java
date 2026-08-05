package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada ao tentar registrar o empréstimo de um livro que
 * já foi emprestado anteriormente.
 */
public class LivroJaEmprestadoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public LivroJaEmprestadoException(String message) {
        super(message);
    }
}
