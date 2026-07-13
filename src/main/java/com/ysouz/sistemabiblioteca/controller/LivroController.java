package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.service.LivroService;
import com.ysouz.sistemabiblioteca.validation.LivroValidator;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.exception.LivroJaCadastradoException;

import java.util.InputMismatchException;
import java.util.Scanner;

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

            } catch (IllegalArgumentException | LivroJaCadastradoException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números para o ano de lançamento.");
                this.scanner.nextLine();
            }
        }
        System.out.println("Livro cadastrado!");
    }
}
