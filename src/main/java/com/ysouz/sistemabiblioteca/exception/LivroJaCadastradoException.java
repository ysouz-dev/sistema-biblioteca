package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando o sistema tenta cadastrar um livro com o mesmo
 * código isbn de um livro já cadastrado anteriormente.
 */
public class LivroJaCadastradoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public LivroJaCadastradoException(String message) {
        super(message);
    }

    /**
     * Cria a exceção com uma mensagem de erro e a causa original do erro.
     *
     * @param message descrição do erro
     * @param cause exceção original que motivou este erro
     */
    public LivroJaCadastradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
