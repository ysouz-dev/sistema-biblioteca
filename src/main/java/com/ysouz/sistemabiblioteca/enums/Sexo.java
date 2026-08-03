package com.ysouz.sistemabiblioteca.enums;

import java.util.Objects;

/**
 * Representa o sexo do usuário no sistema.
 */
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

    public String getNome() {
        return this.nome;
    }

    /**
     * Converte a sigla (M/F/I) ou o nome (MASCULINO/FEMININO/INDEFINIDO) para o sexo correspondente.
     *
     * @param sexo sigla ou nome do sexo
     * @return o Sexo correspondente a sigla ou o nome
     * @throws IllegalArgumentException se a sigla ou o nome não corresponder com nenhum sexo válido
     */
    public static Sexo toSexo(String sexo) {
        for (Sexo sex : Sexo.values()) {
            if (sexo.equalsIgnoreCase(sex.getNome()) || sexo.equalsIgnoreCase(sex.getSigla())) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Sexo inválido.");
    }

    public static boolean isNull(Sexo sexo) {
        return Objects.isNull(sexo);
    }
}
