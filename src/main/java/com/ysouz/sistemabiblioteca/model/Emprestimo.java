package com.ysouz.sistemabiblioteca.model;

import java.time.LocalDate;
import java.util.Objects;

public class Emprestimo {
    private Usuario usuario;
    private Livro livro;
    private LocalDate data;

    public Emprestimo(Usuario usuario, Livro livro) {
        if (Objects.isNull(usuario)) {throw new IllegalArgumentException("O usuário não pode ser nulo");}
        if (Objects.isNull(livro)) {throw new IllegalArgumentException("O livro não pode ser nulo.");}
        if (!livro.isDisponivel()) {throw new IllegalArgumentException("Um livro indisponivel não pode ser emprestado");}

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
