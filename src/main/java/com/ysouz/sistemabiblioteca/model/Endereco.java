package com.ysouz.sistemabiblioteca.model;

import com.ysouz.sistemabiblioteca.validation.EnderecoValidator;

/**
 * Representa um endereço de um {@link Usuario} do sistema.
 */
public class Endereco {
    private final String rua;
    private final String bairro;
    private final String numero;
    private final String cep;

    /**
     * Cria um Endereço validando os dados informados.
     *
     * @param rua nome da rua
     * @param numero número da residência
     * @param bairro nome do bairro
     * @param cep CEP da rua
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos
     *          (rua, número, bairro ou cep nulos/inválidos)
     */
    public Endereco(String rua, String numero, String bairro, String cep) {
        EnderecoValidator.validaLogradouro(rua, "rua");
        EnderecoValidator.validaLogradouro(numero, "número");
        EnderecoValidator.validaLogradouro(bairro, "bairro");
        EnderecoValidator.validaCep(cep);

        this.rua = rua.strip().toUpperCase();
        this.bairro = bairro.strip().toUpperCase();
        this.numero = numero.strip().toUpperCase();
        this.cep = cep.strip();
    }

    public String getRua() {
        return this.rua;
    }

    public String getBairro() {
        return this.bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public String getNumero() {
        return this.numero;
    }
}
