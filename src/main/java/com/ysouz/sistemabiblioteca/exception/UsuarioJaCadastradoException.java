package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando o sistema tenta cadastrar um usuário com o mesmo
 * CPF de um usuário já cadastrado anteriormente.
 */
public class UsuarioJaCadastradoException extends RuntimeException {

    /**
     * Cria a exceção com uma messagem de erro.
     *
     * @param message descrição do erro
     */
    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
}
