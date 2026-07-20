package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.repository.LivroRepository;
import com.ysouz.sistemabiblioteca.exception.LivroJaCadastradoException;

import java.util.List;

public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService() {
        this.livroRepository = new LivroRepository();
    }

    public void cadastrarLivro(Livro livro) {
        if (this.livroRepository.containsLivro(livro.getIsbn())) {
            throw new LivroJaCadastradoException("Livro já cadastrado no sistema.");
        }
        this.livroRepository.salvar(livro);
    }

    public Livro buscarLivroPorIsbn(String isbn) {
        return this.livroRepository.buscaPorIsbn(isbn);
    }

    public List<Livro> buscarLivroPorAutor(String autor) {
        return this.livroRepository.buscaPorAutor(autor);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        return this.livroRepository.buscaPorTitulo(titulo);
    }
}
