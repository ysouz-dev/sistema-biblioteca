package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.LivroValidator;

public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private int anoLancamento;
    private boolean disponivel;

    public Livro (String titulo, String autor, String isbn, int anoLancamento) {
        LivroValidator.validaTitulo(titulo);
        LivroValidator.validaAutor(autor);
        LivroValidator.validaIsbn(isbn);
        LivroValidator.validaAnoLancamento(anoLancamento);

        this.titulo = titulo.strip().toUpperCase();
        this.autor = autor.strip().toUpperCase();
        this.isbn = isbn.strip();
        this.anoLancamento = anoLancamento;
        this.disponivel = true;
    }
}
