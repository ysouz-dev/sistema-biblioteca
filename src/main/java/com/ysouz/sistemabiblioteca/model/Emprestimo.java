package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.exception.LivroJaEmprestadoException;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa o empréstimo de um {@link Livro} a um {@link Usuario} do sistema.
 */
public class Emprestimo {
    private Usuario usuario;
    private Livro livro;
    private LocalDate data;

    /**
     * Cria um empréstimo validando os dados informados.
     *
     * @param usuario usuário que realizará o empréstimo
     * @param livro livro que será emprestado
     * @throws IllegalArgumentException se o usuário ou o livro forem nulos
     * @throws LivroJaEmprestadoException se o livro a ser emprestado já tiver sido emprestado anteriormente
     */
    public Emprestimo(Usuario usuario, Livro livro) {
        if (Objects.isNull(usuario)) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }
        if (Objects.isNull(livro)) {
            throw new IllegalArgumentException("O livro não pode ser nulo.");
        }
        if (!livro.isDisponivel()) {
            throw new LivroJaEmprestadoException("Livro indisponivel! Não pode ser emprestado.");
        }

        this.usuario = usuario;
        this.livro = livro;
        this.data = LocalDate.now();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Livro getLivro() {
        return this.livro;
    }

    public LocalDate getData() {
        return this.data;
    }
}
