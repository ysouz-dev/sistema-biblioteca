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
}
