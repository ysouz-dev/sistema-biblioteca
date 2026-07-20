package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.exception.*;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.service.EmprestimoService;
import com.ysouz.sistemabiblioteca.service.UsuarioService;
import com.ysouz.sistemabiblioteca.service.LivroService;
import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.validation.LivroValidator;

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

            } catch (UsuarioNaoEncontradoException | EnderecoNaoEncontradoException | LivroNaoEncontradoException |
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
}
