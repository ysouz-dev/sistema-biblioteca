package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;

public class Usuario {
    private String nome;
    private String cpf;
    private Endereco endereco;

    public Usuario(String nome, String cpf, Endereco endereco) {
        UsuarioValidator.validaNome(nome);
        UsuarioValidator.validaCpf(cpf);
        UsuarioValidator.validaEndereco(endereco);

        this.nome = nome.strip().toUpperCase();
        this.cpf = cpf.strip();
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
}
