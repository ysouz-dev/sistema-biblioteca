package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
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
        List<Livro> lista = this.livroRepository.buscaPorAutor(autor);
        if (lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
        }
        return lista;
    }

    public List<Livro> buscarLivroPorTitulo(String titulo) {
        List<Livro> lista = this.livroRepository.buscaPorTitulo(titulo);
        if (lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
        }
        return lista;
    }
}
