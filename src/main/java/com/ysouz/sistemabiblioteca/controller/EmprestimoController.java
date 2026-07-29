package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.exception.*;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.dto.EmprestimoDTO;
import com.ysouz.sistemabiblioteca.service.EmprestimoService;
import com.ysouz.sistemabiblioteca.service.UsuarioService;
import com.ysouz.sistemabiblioteca.service.LivroService;
import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.validation.LivroValidator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class EmprestimoController {
    private final Scanner scanner;
    private final EmprestimoService emprestimoService;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoController(Scanner scanner) {
        this.scanner = scanner;
        this.emprestimoService = new EmprestimoService();
        this.usuarioService = new UsuarioService();
        this.livroService = new LivroService();

    }

    public void cadastrarEmprestimo() {
        System.out.println("========= Cadastrar Empréstimo =========");

        Usuario usuario = null;
        Livro livro = null;

        int contador = 1;

        while (true) {
            try {
                if (contador == 1) {
                    System.out.print("Cpf do usuário: ");
                    String cpf = this.scanner.nextLine();
                    UsuarioValidator.validaCpf(cpf);

                    usuario = this.usuarioService.buscarUsuarioPorCpf(cpf);
                    contador++;
                }

                if (contador == 2) {
                    System.out.print("ISBN do livro: ");
                    String isbn = this.scanner.nextLine();
                    LivroValidator.validaIsbn(isbn);

                    livro = this.livroService.buscarLivroPorIsbn(isbn);
                    contador++;
                }

                this.emprestimoService.cadastrarEmprestimo(new Emprestimo(usuario, livro));
                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (UsuarioNaoEncontradoException | DadoInconsistenteException | LivroNaoEncontradoException |
                     EmprestimoPendenteException | LivroJaEmprestadoException e) {

                System.out.println(e.getMessage());
                return;

            }
        }
        System.out.println("Empréstimo cadastrado!");
    }

    public void devolverLivro() {
        System.out.println("========= DEVOLUÇÃO =========");

        String cpf = "";

        int contador = 0;
        while (contador == 0) {
            try {
                System.out.print("CPF do usuário: ");
                cpf = this.scanner.nextLine();
                UsuarioValidator.validaCpf(cpf);
                contador++;

                this.emprestimoService.devolverLivro(cpf);


            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (EmprestimoNaoEncontradoException | LivroNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("Devolução realizada!");
    }

    public void buscaEmprestimoPendentePorCpf() {
        System.out.println("========= Busca de Empréstimo Pendente =========");

        EmprestimoDTO emprestimo = null;

        while (Objects.isNull(emprestimo)) {
            try {
                System.out.print("Cpf do usuário: ");
                String cpf = this.scanner.nextLine();
                UsuarioValidator.validaCpf(cpf);

                emprestimo = this.emprestimoService.buscaEmprestimoPendentePorCpf(cpf);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (EmprestimoNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        System.out.printf("==========ID: %d===========%n", emprestimo.getId());
        System.out.println("Usuário: " + emprestimo.getNomeUsuario());
        System.out.println("Livro: " + emprestimo.getNomeLivro());
        int dia = emprestimo.getData().getDayOfMonth();
        int mes = emprestimo.getData().getMonthValue();
        int ano = emprestimo.getData().getYear();
        System.out.printf("Data: %d/%d/%d %n", dia, mes, ano);
        System.out.println("Situação: " + emprestimo.getSituacao());
        System.out.println("=====================");
    }

    public void buscaTodosEmprestimosPorCpf() {
        System.out.println("========= Busca Todos Empréstimo Por CPF =========");

        List<EmprestimoDTO> lista = null;

        while (Objects.isNull(lista)) {
            try {
                System.out.print("Cpf do usuário: ");
                String cpf = this.scanner.nextLine();
                UsuarioValidator.validaCpf(cpf);

                lista = this.emprestimoService.buscaTodosEmprestimosPorCpf(cpf);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (EmprestimoNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        for (EmprestimoDTO emprestimo : lista) {
            System.out.printf("==========ID: %d===========%n", emprestimo.getId());
            System.out.println("Usuário: " + emprestimo.getNomeUsuario());
            System.out.println("Livro: " + emprestimo.getNomeLivro());
            int dia = emprestimo.getData().getDayOfMonth();
            int mes = emprestimo.getData().getMonthValue();
            int ano = emprestimo.getData().getYear();
            System.out.printf("Data: %d/%d/%d %n", dia, mes, ano);
            System.out.println("Situação: " + emprestimo.getSituacao());
            System.out.println("=====================");
        }
    }
}
