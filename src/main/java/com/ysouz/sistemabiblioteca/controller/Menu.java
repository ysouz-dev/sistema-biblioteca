package com.ysouz.sistemabiblioteca.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final LivroController livroController;
    private final UsuarioController usuarioController;
    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.usuarioController = new UsuarioController(this.scanner);
        this.livroController = new LivroController(this.scanner);
    }

    public int menuPrincipal() {
        String titulo = "   Biblioteca YS   ";
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("| " + titulo + " |");
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("[ 1 ] Cadastrar Livro");
        System.out.println("[ 2 ] Cadastrar Usuário");
        System.out.println("[ 0 ] Encerrar Sistema");
        System.out.println("-".repeat(25));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = this.scanner.nextInt();
                this.scanner.nextLine();
                if (resposta < 0 || resposta > 2) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                this.scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 2);
        return resposta;
    }

    public void cadastrarLivro() {
        this.livroController.cadastrarLivro();
    }

    public void cadastrarUsuario() {
        this.usuarioController.cadastrarUsuario();
    }

    public void encerrarSistema() {
        System.out.println("Sistema encerrado, volte sempre!");
        this.scanner.close();
    }
}
