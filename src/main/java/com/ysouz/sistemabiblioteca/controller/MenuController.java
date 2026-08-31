package com.ysouz.sistemabiblioteca.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuController {
    private final LivroController livroController;
    private final UsuarioController usuarioController;
    private final EmprestimoController emprestimoController;
    private final Scanner scanner;

    public MenuController(LivroController livroController, UsuarioController usuarioController,
                          EmprestimoController emprestimoController, Scanner scanner) {
        this.scanner = scanner;
        this.usuarioController = usuarioController;
        this.livroController = livroController;
        this.emprestimoController = emprestimoController;
    }

    public int menuPrincipal() {
        String titulo = "   Biblioteca YS   ";
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("| " + titulo + " |");
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("[ 1 ] Cadastrar Livro");
        System.out.println("[ 2 ] Cadastrar Usuário");
        System.out.println("[ 3 ] Registrar Empréstimo");
        System.out.println("[ 4 ] Devolver Livro");
        System.out.println("[ 5 ] Livro Opcões...");
        System.out.println("[ 6 ] Usuário Opções...");
        System.out.println("[ 7 ] Empréstimo Opções...");
        System.out.println("[ 0 ] Encerrar Sistema");
        System.out.println("-".repeat(25));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = this.scanner.nextInt();
                this.scanner.nextLine();
                if (resposta < 0 || resposta > 7) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                this.scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 7);
        return resposta;
    }

    public int menuLivro() {
        System.out.println("========= Livro Opcões ========");
        System.out.println("[ 1 ] Busca por Título");
        System.out.println("[ 2 ] Busca Por ISBN");
        System.out.println("[ 3 ] Busca Por Autor");
        System.out.println("[ 4 ] Livros Disponíveis");
        System.out.println("[ 5 ] Livros Emprestados");
        System.out.println("[ 0 ] Voltar");
        System.out.println("-".repeat(25));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = this.scanner.nextInt();
                this.scanner.nextLine();
                if (resposta < 0 || resposta > 5) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                this.scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 5);
        return resposta;
    }

    public int menuUsuario() {
        System.out.println("========= Usuário Opcões ========");
        System.out.println("[ 1 ] Busca por CPF");
        System.out.println("[ 2 ] Busca por Nome");
        System.out.println("[ 3 ] Usuários com Empréstimos Pendentes");
        System.out.println("[ 4 ] Lista de Usuários");
        System.out.println("[ 0 ] Voltar");
        System.out.println("-".repeat(25));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = this.scanner.nextInt();
                this.scanner.nextLine();
                if (resposta < 0 || resposta > 4) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                this.scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 4);
        return resposta;
    }

    public int menuEmprestimo() {
        System.out.println("========= Empréstimo Opcões ========");
        System.out.println("[ 1 ] Consultar Empréstimos Pendentes Por CPF");
        System.out.println("[ 2 ] Consultar Todos Empréstimos realizados Por CPF");
        System.out.println("[ 3 ] Lista de Todos Empréstimos");
        System.out.println("[ 4 ] Lista de Todos Empréstimos Pendentes");
        System.out.println("[ 0 ] Voltar");
        System.out.println("-".repeat(25));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = this.scanner.nextInt();
                this.scanner.nextLine();
                if (resposta < 0 || resposta > 4) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                this.scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 4);
        return resposta;
    }

    public void iniciar() {
        int opcao = Integer.MIN_VALUE;
        do {
            opcao = menuPrincipal();

            switch (opcao) {
                case 0:
                    System.out.println("Sistema encerrado, volte sempre!");
                    this.scanner.close();
                    break;

                case 1:
                    this.livroController.cadastrarLivro();
                    break;

                case 2:
                    this.usuarioController.cadastrarUsuario();
                    break;

                case 3:
                    this.emprestimoController.cadastrarEmprestimo();
                    break;

                case 4:
                    this.emprestimoController.devolverLivro();
                    break;

                case 5:
                    int opcaoLivro = menuLivro();
                    switch (opcaoLivro) {

                        case 0:
                            break;

                        case 1:
                            this.livroController.buscaPorTitulo();
                            break;

                        case 2:
                            this.livroController.buscaPorIsbn();
                            break;

                        case 3:
                            this.livroController.buscaPorAutor();
                            break;

                        case 4:
                            this.livroController.listaLivrosDisponiveis();
                            break;

                        case 5:
                            this.livroController.listaLivrosEmprestados();
                            break;
                    }
                    break;

                case 6:
                    int opcaoUsuario = menuUsuario();
                    switch (opcaoUsuario) {

                        case 0:
                            break;

                        case 1:
                            this.usuarioController.buscaPorCpf();
                            break;

                        case 2:
                            this.usuarioController.buscaPorNome();
                            break;

                        case 3:
                            this.usuarioController.listaUsuariosPendentes();
                            break;

                        case 4:
                            this.usuarioController.listaUsuarios();
                            break;
                    }
                    break;

                case 7:
                    int opcaoEmprestimo = menuEmprestimo();
                    switch (opcaoEmprestimo) {
                        case 0:
                            break;

                        case 1:
                            this.emprestimoController.buscaEmprestimoPendentePorCpf();
                            break;

                        case 2:
                            this.emprestimoController.buscaTodosEmprestimosPorCpf();
                            break;

                        case 3:
                            this.emprestimoController.listaTodosEmprestimos();
                            break;

                        case 4:
                            this.emprestimoController.listaTodosEmprestimosPendentes();
                            break;
                    }
                    break;
            }
        } while (opcao != 0);
    }
}
