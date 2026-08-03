package com.ysouz.sistemabiblioteca.dto;

import java.time.LocalDate;

public class EmprestimoDTO {
    private final int id;
    private final String nomeUsuario;
    private final String nomeLivro;
    private final LocalDate data;
    private final String situacao;

    public EmprestimoDTO(int id, String nomeUsuario, String nomeLivro, LocalDate data, String situacao) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.nomeLivro = nomeLivro;
        this.data = data;
        this.situacao = situacao;
    }

    public int getId() {
        return this.id;
    }

    public String getNomeUsuario() {
        return this.nomeUsuario;
    }

    public String getNomeLivro() {
        return this.nomeLivro;
    }

    public LocalDate getData() {
        return this.data;
    }

    public String getSituacao() {
        return this.situacao;
    }
}
