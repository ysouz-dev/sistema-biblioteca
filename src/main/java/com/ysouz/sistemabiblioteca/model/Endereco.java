package com.ysouz.sistemabiblioteca.model;

public class Endereco {
    private String rua;
    private String bairro;
    private Integer numero;
    private String cep;

    public Endereco(String rua, Integer numero, String bairro, String cep) {
        this.rua = rua.strip().toUpperCase();
        this.bairro = bairro.strip().toUpperCase();
        this.numero = numero;
        this.cep = cep.strip();
    }
}
