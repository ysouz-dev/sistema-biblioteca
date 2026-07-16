package com.ysouz.sistemabiblioteca.validation;

import com.ysouz.sistemabiblioteca.enums.Sexo;
import com.ysouz.sistemabiblioteca.model.Endereco;

import java.util.Objects;

public final class UsuarioValidator {

    public static void validaNome(String nome) {
         if (Objects.isNull(nome) || nome.strip().length() < 3 || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

         String nomeSemEspaco = nome.replace(" ", "");
         for (int i = 0; i < nomeSemEspaco.length(); i++) {
             if (!Character.isLetter(nomeSemEspaco.charAt(i))) {
                 throw new IllegalArgumentException("Nome inválido.");
             }
         }
    }

    public static void validaCpf(String cpf) {
        if (Objects.isNull(cpf) || cpf.strip().length() != 11 || cpf.isBlank()) {
            throw new IllegalArgumentException("Cpf inválido.");
        }

        for (int i = 0; i < cpf.strip().length(); i++) {
            if (!Character.isDigit(cpf.strip().charAt(i))) {
                throw new IllegalArgumentException("Cpf invalido.");
            }
        }
    }

    public static void validaEndereco(Endereco endereco) {
        if (Objects.isNull(endereco)) {
            throw new IllegalArgumentException("Endereço inválido.");
        }
    }

    public static void validaSexo(Sexo sexo) {
        if (Sexo.isNull(sexo)) {
            throw new IllegalArgumentException("Sexo inválido.");
        }
    }
}
