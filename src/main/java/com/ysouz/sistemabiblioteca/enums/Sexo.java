package com.ysouz.sistemabiblioteca.enums;

public enum Sexo {
    MASCULINO("MASCULINO", "M"),
    FEMININO("FEMININO","F");

    private String nome;
    private String sigla;

    Sexo(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }
}
