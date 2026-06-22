package com.ysouz.sistemabiblioteca.model;

public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private int anoLancamento;
    private boolean disponivel;

    public Livro (String titulo, String autor, String isbn, int anoLancamento) {
        this.titulo = titulo.strip().toUpperCase();
        this.autor = autor.strip().toUpperCase();
        this.isbn = isbn.strip();
        this.anoLancamento = anoLancamento;
    }
}
