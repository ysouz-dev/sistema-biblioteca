package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.UsuarioValidator;
import com.ysouz.sistemabiblioteca.enums.Sexo;

public class Usuario {
    private String nome;
    private Sexo sexo;
    private String cpf;
    private Endereco endereco;

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
