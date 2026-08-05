package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.dto.UsuarioDTO;
import com.ysouz.sistemabiblioteca.exception.DadoInconsistenteException;
import com.ysouz.sistemabiblioteca.exception.UsuarioNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Endereco;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.service.UsuarioService;
import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.enums.Sexo;
import com.ysouz.sistemabiblioteca.exception.UsuarioJaCadastradoException;

import java.util.List;
import java.util.Objects;
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
        System.out.println("Usuário cadastrado!");
    }

    public void buscaPorCpf() {

        Usuario usuario = null;

        while (Objects.isNull(usuario)) {
            try {
                System.out.print("Cpf do usuário: ");
                String cpf = this.scanner.nextLine();
                UsuarioValidator.validaCpf(cpf);

                usuario = this.usuarioService.buscarUsuarioPorCpf(cpf);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (UsuarioNaoEncontradoException | DadoInconsistenteException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("=====================");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Cpf: " + usuario.getCpf());
        System.out.println("Sexo: " + usuario.getSexo().getNome());
        System.out.println("CEP: " + usuario.getEndereco().getCep());
        System.out.println("Rua: " + usuario.getEndereco().getRua());
        System.out.println("Bairro: " + usuario.getEndereco().getBairro());
        System.out.println("Número: " + usuario.getEndereco().getNumero());
        System.out.println("=====================");

    }

    public void buscaPorNome() {

        List<Usuario> lista = null;

        while (Objects.isNull(lista)) {
            try {
                System.out.print("Nome do usuário: ");
                String nome = this.scanner.nextLine().strip().toUpperCase();
                UsuarioValidator.validaNome(nome);

                lista = this.usuarioService.buscarUsuarioPorNome(nome);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (UsuarioNaoEncontradoException | DadoInconsistenteException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        for (Usuario user : lista) {
            System.out.println("=====================");
            System.out.println("Nome: " + user.getNome());
            System.out.println("Cpf: " + user.getCpf());
            System.out.println("Sexo: " + user.getSexo().getNome());
            System.out.println("CEP: " + user.getEndereco().getCep());
            System.out.println("Rua: " + user.getEndereco().getRua());
            System.out.println("Bairro: " + user.getEndereco().getBairro());
            System.out.println("Número: " + user.getEndereco().getNumero());
            System.out.println("=====================");
        }
    }

    public void listaUsuariosPendentes() {
        try {
            List<UsuarioDTO> lista = this.usuarioService.listaUsuariosPendente();
            for (int i = 0; i < lista.size(); i++) {
                System.out.println("=====================");
                System.out.printf("%d.%n", i + 1);
                System.out.println("Nome: " + lista.get(i).getNome());
                System.out.println("Cpf: " + lista.get(i).getCpf());
                System.out.println("Sexo: " + lista.get(i).getSexo().getNome());
                System.out.println("=====================");
            }
        } catch (UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listaUsuarios() {
        try {
            List<UsuarioDTO> lista = this.usuarioService.listaUsuarios();

            for (int i = 0; i < lista.size(); i++) {
                System.out.println("=====================");
                System.out.printf("%d.%n", i + 1);
                System.out.println("Nome: " + lista.get(i).getNome());
                System.out.println("Cpf: " + lista.get(i).getCpf());
                System.out.println("Sexo: " + lista.get(i).getSexo().getNome());
                System.out.println("=====================");
            }

        } catch (UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}
