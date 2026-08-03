package com.ysouz.sistemabiblioteca.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuController {
    private final LivroController livroController;
    private final UsuarioController usuarioController;
    private final EmprestimoController emprestimoController;
    private final Scanner scanner;

    public MenuController() {
        this.scanner = new Scanner(System.in);
        this.usuarioController = new UsuarioController(this.scanner);
        this.livroController = new LivroController(this.scanner);
        this.emprestimoController = new EmprestimoController(this.scanner);
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
        System.out.println("[ 5 ] Livros Pendentes");
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

    public void cadastrarLivro() {
        this.livroController.cadastrarLivro();
    }

    public void cadastrarUsuario() {
        this.usuarioController.cadastrarUsuario();
    }

    public void registrarEmprestimo() {
        this.emprestimoController.cadastrarEmprestimo();
    }

    public void devolverLivro() {
        this.emprestimoController.devolverLivro();
    }

    public void buscarPorTitulo() {
        this.livroController.buscaPorTitulo();
    }

    public void buscarPorAutor() {
        this.livroController.buscaPorAutor();
    }

    public void buscarPorIsbn() {
        this.livroController.buscaPorIsbn();
    }

    public void listaLivrosDisponiveis() {
        this.livroController.listaLivrosDisponiveis();
    }

    public void listaLivrosPendentes() {
        this.livroController.listaLivrosPendentes();
    }

    public void buscaPorCpf() {
        this.usuarioController.buscaPorCpf();
    }

    public void buscaPorNome() {
        this.usuarioController.buscaPorNome();
    }

    public void listaUsuariosPendentes() {
        this.usuarioController.listaUsuariosPendentes();
    }

    public void listaUsuarios() {
        this.usuarioController.listaUsuarios();
    }

    public void buscaEmprestimoPendentePorCpf() {
        this.emprestimoController.buscaEmprestimoPendentePorCpf();
    }

    public void buscaTodosEmprestimosPorCpf() {
        this.emprestimoController.buscaTodosEmprestimosPorCpf();
    }

    public void listaTodosEmprestimos() {
        this.emprestimoController.listaTodosEmprestimos();
    }

    public void listaTodosEmprestismosPendentes() {
        this.emprestimoController.listaTodosEmprestimosPendentes();
    }

    public void encerrarSistema() {
        System.out.println("Sistema encerrado, volte sempre!");
        this.scanner.close();
    }
}
