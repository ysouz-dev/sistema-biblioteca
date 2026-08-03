package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.LivroValidator;

/**
 * Representa um livro no sistema.
 */
public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private int anoLancamento;
    private boolean disponivel;

    /**
     * Cria um livro validando os dados informados.
     *
     * @param titulo título do livro
     * @param autor autor do livro
     * @param isbn código isbn do livro
     * @param anoLancamento ano de lançamento do livro
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos
     *         (título, autor, isbn ou ano de lançamento nulos/inválidos)
     */
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

    public Livro(String titulo, String autor, String isbn, int anoLancamento, boolean disponivel) {
        LivroValidator.validaTitulo(titulo);
        LivroValidator.validaAutor(autor);
        LivroValidator.validaIsbn(isbn);
        LivroValidator.validaAnoLancamento(anoLancamento);

        this.titulo = titulo.strip().toUpperCase();
        this.autor = autor.strip().toUpperCase();
        this.isbn = isbn.strip();
        this.anoLancamento = anoLancamento;
        this.disponivel = disponivel;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getAnoLancamento() {
        return this.anoLancamento;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }
}
