package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.model.Endereco;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.service.UsuarioService;
import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.enums.Sexo;
import com.ysouz.sistemabiblioteca.exception.UsuarioJaCadastradoException;

import java.util.Scanner;

public class UsuarioController {
    private final Scanner scanner;
    private EnderecoController enderecoController;
    private UsuarioService usuarioService;

    public UsuarioController(Scanner scanner) {
        this.scanner = scanner;
        this.enderecoController = new EnderecoController(scanner);
        this.usuarioService = new UsuarioService();
    }

    public void cadastrarUsuario() {
        System.out.println("========= Cadastro Usuário =========");

        String nome = "";
        Sexo sexo = Sexo.INDEFINIDO;
        String cpf = "";
        Endereco endereco = null;

        int contador = 1;

        while (true) {
            try {
                if (contador == 1) {
                    System.out.print("Nome: ");
                    nome = this.scanner.nextLine();
                    UsuarioValidator.validaNome(nome);
                    contador++;
                }

                if (contador == 2) {
                    System.out.print("Sexo: ");
                    String sex = this.scanner.nextLine();
                    sexo = Sexo.toSexo(sex);
                    contador++;
                }

                if (contador == 3) {
                    System.out.print("CPF: ");
                    cpf = this.scanner.nextLine();
                    UsuarioValidator.validaCpf(cpf);
                    contador++;
                }

                endereco = enderecoController.cadastrarEndereco();

                this.usuarioService.cadastrarUsuario(new Usuario(nome, cpf, sexo, endereco));

                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (UsuarioJaCadastradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
