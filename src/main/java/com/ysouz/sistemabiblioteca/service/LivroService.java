package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.repository.LivroRepository;
import com.ysouz.sistemabiblioteca.exception.LivroJaCadastradoException;

public class LivroService {
    private LivroRepository livroRepository;

    public LivroService() {
        this.livroRepository = new LivroRepository();
    }

    public void cadastrarLivro(Livro livro) {
        if (this.livroRepository.containsLivro(livro.getIsbn())) {
            throw new LivroJaCadastradoException("Livro já cadastrado no sistema.");
        }
        this.livroRepository.salvar(livro);
    }
}
