package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
import com.ysouz.sistemabiblioteca.service.LivroService;
import com.ysouz.sistemabiblioteca.validation.LivroValidator;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.exception.LivroJaCadastradoException;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;

public class LivroController {
    private final Scanner scanner;
    private final LivroService livroService;

    public LivroController(Scanner scanner) {
        this.scanner = scanner;
        this.livroService = new LivroService();
    }

    public void cadastrarLivro () {
        System.out.println("========= Cadastro Livro =========");

        String titulo = "";
        String autor = "";
        String isbn = "";
        int anoLancamento = 0;

        int valor = 1;

        while (true) {
            try {
                if (valor == 1) {
                    System.out.print("Título: ");
                    titulo = this.scanner.nextLine();
                    LivroValidator.validaTitulo(titulo);
                    valor++;
                }

                if (valor == 2) {
                    System.out.print("Autor: ");
                    autor = this.scanner.nextLine();
                    LivroValidator.validaAutor(autor);
                    valor++;
                }

                if (valor == 3) {
                    System.out.print("ISBN: ");
                    isbn = this.scanner.nextLine();
                    LivroValidator.validaIsbn(isbn);
                    valor++;
                }

                if (valor == 4) {
                    System.out.print("Ano de lançamento: ");
                    anoLancamento = this.scanner.nextInt();
                    this.scanner.nextLine();
                    LivroValidator.validaAnoLancamento(anoLancamento);
                    valor++;
                }

                this.livroService.cadastrarLivro(new Livro(titulo, autor, isbn, anoLancamento));

                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números para o ano de lançamento.");
                this.scanner.nextLine();

            } catch (LivroJaCadastradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("Livro cadastrado!");
    }

    public void buscaPorIsbn() {
        System.out.println("========= Busca por isbn =========");

        String isbn = "";
        Livro livro = null;

        while (Objects.isNull(livro)) {
            try {
                System.out.print("ISBN: ");
                isbn = this.scanner.nextLine().strip();
                LivroValidator.validaIsbn(isbn);

                livro = this.livroService.buscarLivroPorIsbn(isbn);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (LivroNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        System.out.println("=====================");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Lançamento: " + livro.getAnoLancamento());
        System.out.println("Disponível: " + ((livro.isDisponivel()) ? "SIM" : "NÃO"));
        System.out.println("=====================");
    }

    public void buscaPorTitulo() {
        System.out.println("========= Busca por título =========");

        String titulo = "";
        List<Livro> livros = null;

        while (Objects.isNull(livros)) {
            try {
                System.out.print("Título: ");
                titulo = this.scanner.nextLine().strip().toUpperCase();
                LivroValidator.validaTitulo(titulo);

                livros = this.livroService.buscarLivroPorTitulo(titulo);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (LivroNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        for (Livro book : livros) {
            System.out.println("=====================");
            System.out.println("Título: " + book.getTitulo());
            System.out.println("Autor: " + book.getAutor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Lançamento: " + book.getAnoLancamento());
            System.out.println("Disponível: " + ((book.isDisponivel()) ? "SIM" : "NÃO"));
            System.out.println("=====================");
        }
    }

    public void buscaPorAutor() {
        System.out.println("========= Busca por Autor =========");

        String autor = "";
        List<Livro> livros = null;

        while (Objects.isNull(livros)) {
            try {
                System.out.print("Autor: ");
                autor = this.scanner.nextLine().strip().toUpperCase();
                LivroValidator.validaAutor(autor);

                livros = this.livroService.buscarLivroPorAutor(autor);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (LivroNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        for (Livro book : livros) {
            System.out.println("=====================");
            System.out.println("Título: " + book.getTitulo());
            System.out.println("Autor: " + book.getAutor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Lançamento: " + book.getAnoLancamento());
            System.out.println("Disponível: " + ((book.isDisponivel()) ? "SIM" : "NÃO"));
            System.out.println("=====================");
        }
    }

    public void listaLivrosDisponiveis() {
        System.out.println("========= Livros Disponíveis =========");
        try {
            List<Livro> lista = this.livroService.listaLivrosDisponiveis();
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - %s | Autor: %s %n", i+1, lista.get(i).getTitulo(), lista.get(i).getAutor());
            }
        } catch (LivroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listaLivrosPendentes() {
        System.out.println("========= Livros Pendentes =========");
        try {
            List<Livro> lista = this.livroService.listaLivrosPendentes();
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%d - %s | Autor: %s %n", i + 1, lista.get(i).getTitulo(), lista.get(i).getAutor());
            }
        } catch (LivroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}
