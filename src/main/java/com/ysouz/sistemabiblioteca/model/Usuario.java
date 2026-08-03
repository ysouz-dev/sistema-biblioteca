package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.enums.Sexo;

/**
 * Representa um usuário no sistema.
 */
public class Usuario {
    private String nome;
    private Sexo sexo;
    private String cpf;
    private Endereco endereco;

    /**
     * Cria um usuário validando os dados informados.
     *
     * @param nome nome do usuário
     * @param cpf CPF do usuário
     * @param sexo sexo do usuário
     * @param endereco endereco do usuário
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos
     *          (nome, CPF, sexo ou endereços nulos/inválidos)
     */
    public Usuario(String nome, String cpf, Sexo sexo,Endereco endereco) {
        UsuarioValidator.validaNome(nome);
        UsuarioValidator.validaCpf(cpf);
        UsuarioValidator.validaSexo(sexo);
        UsuarioValidator.validaEndereco(endereco);

        this.nome = nome.strip().toUpperCase();
        this.cpf = cpf.strip();
        this.sexo = sexo;
        this.endereco = endereco;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public Sexo getSexo() {
        return this.sexo;
    }
}
