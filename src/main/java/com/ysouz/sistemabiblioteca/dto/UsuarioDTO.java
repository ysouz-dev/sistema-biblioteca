package com.ysouz.sistemabiblioteca.dto;

import com.ysouz.sistemabiblioteca.enums.Sexo;

public class UsuarioDTO {
    private final String nome;
    private final String cpf;
    private final Sexo sexo;

    public UsuarioDTO(String nome, String cpf, Sexo sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Sexo getSexo() {
        return this.sexo;
    }
}
