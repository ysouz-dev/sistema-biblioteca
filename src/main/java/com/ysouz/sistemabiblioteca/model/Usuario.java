package com.ysouz.sistemabiblioteca.model;

public class Usuario {
    private String nome;
    private String cpf;
    private Endereco endereco;

    public Usuario(String nome, String cpf, Endereco endereco) {
        this.nome = nome.strip().toUpperCase();
        this.cpf = cpf.strip();
        this.endereco = endereco;
    }
}
