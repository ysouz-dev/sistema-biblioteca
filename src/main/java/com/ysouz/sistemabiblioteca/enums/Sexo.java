package com.ysouz.sistemabiblioteca.enums;

import java.util.Objects;

public enum Sexo {
    MASCULINO("MASCULINO", "M"),
    FEMININO("FEMININO","F"),
    INDEFINIDO("INDEFINIDO", "I");

    private String nome;
    private String sigla;

    Sexo(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getSigla() {
        return this.sigla;
    }

    public static boolean isNull(Sexo sexo) {
        return Objects.isNull(sexo);
    }
}
