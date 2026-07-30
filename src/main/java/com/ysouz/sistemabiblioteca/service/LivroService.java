package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.repository.LivroRepository;
import com.ysouz.sistemabiblioteca.exception.LivroJaCadastradoException;

import java.util.List;

/**
 * Serviço responsável pelas regras de negócios relacionadas aos livros,
 * incluindo cadastro, busca, listagem e validação de integridade dos dados.
 */
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService() {
        this.livroRepository = new LivroRepository();
    }

    /**
     * Cadastra o livro informado no sistema.
     *
     * @param livro livro a ser cadastrado no sistema
     * @throws LivroJaCadastradoException se o sistema já possuir um livro cadastrado com o mesmo isbn
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public void cadastrarLivro(Livro livro) {
        if (this.livroRepository.containsLivro(livro.getIsbn())) {
            throw new LivroJaCadastradoException("Livro já cadastrado no sistema.");
        }
        this.livroRepository.salvar(livro);
    }

    /**
     * Busca um livro de acordo com o isbn informado.
     *
     * @param isbn isbn do livro a ser buscado
     * @return livro encontrado referente a busca pelo código isbn
     * @throws LivroNaoEncontradoException se nenhum livro for encontrado com o isbn informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public Livro buscarLivroPorIsbn(String isbn) {
        return this.livroRepository.buscaPorIsbn(isbn);
    }

    /**
     * Busca os livros de acordo com o autor informado.
     *
     * @param autor autor dos livros a serem buscados
     * @return uma lista dos livro encontrados referente a busca pelo nome do autor
     * @throws LivroNaoEncontradoException se nenhum livro for encontrado com o autor informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<Livro> buscarLivroPorAutor(String autor) {
        List<Livro> lista = this.livroRepository.buscaPorAutor(autor);
        if (lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
        }
        return lista;
    }

    /**
     * Busca os livros de acordo com o título informado.
     *
     * @param titulo título dos livros a serem buscados
     * @return uma lista dos livros encontrados referente a busca pelo título
     * @throws LivroNaoEncontradoException se nenhum livro for encontrado com o título informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<Livro> buscarLivroPorTitulo(String titulo) {
        List<Livro> lista = this.livroRepository.buscaPorTitulo(titulo);
        if (lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
        }
        return lista;
    }

    /**
     * Lista todos os livros disponíveis para empréstimo.
     *
     * @return uma lista dos livros disponíveis no sistema
     * @throws LivroNaoEncontradoException se nenhum livro estiver disponível para empréstimo
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<Livro> listaLivrosDisponiveis() {
        List<Livro> lista = this.livroRepository.livrosDisponiveis();
        if(lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Não há nenhum livro disponivel no momento");
        }
        return lista;
    }

    /**
     * Lista todos os livros emprestados.
     *
     * @return uma lista dos livros emprestados para os usuários
     * @throws LivroNaoEncontradoException se nenhum livro tiver sido emprestado
     * @throws DatabaseException se ocorrer um erro ao acessar
     */
    public List<Livro> listaLivrosPendentes() {
        List<Livro> lista = this.livroRepository.livrosPendentes();
        if (lista.isEmpty()) {
            throw new LivroNaoEncontradoException("Não há nenhum livro pendente no sistema.");
        }
        return lista;
    }
}
