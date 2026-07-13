package com.ysouz.sistemabiblioteca.model;

public class Endereco {
    private String rua;
    private String bairro;
    private Integer numero;

    public Endereco(String rua, Integer numero, String bairro) {
        this.rua = rua.strip().toUpperCase();
        this.bairro = bairro.strip().toUpperCase();
        this.numero = numero;
    }
}
