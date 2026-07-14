package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.EnderecoValidator;

public class Endereco {
    private String rua;
    private String bairro;
    private String numero;
    private String cep;

    public Endereco(String rua, String numero, String bairro, String cep) {
        EnderecoValidator.validaLogradouro(rua, "rua");
        EnderecoValidator.validaLogradouro(numero, "número");
        EnderecoValidator.validaLogradouro(bairro, "bairro");
        EnderecoValidator.validaCep(cep);

        this.rua = rua.strip().toUpperCase();
        this.bairro = bairro.strip().toUpperCase();
        this.numero = numero;
        this.cep = cep.strip();
    }
}
