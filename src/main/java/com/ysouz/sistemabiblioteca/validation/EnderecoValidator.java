package com.ysouz.sistemabiblioteca.validation;

import java.util.Objects;

public final class EnderecoValidator {

    public static void validaLogradouro(String logradouro, String campo) {
        if (Objects.isNull(logradouro) || logradouro.isBlank()) {
            throw new IllegalArgumentException("Endereço de " + campo + " inválido.");
        }
    }

    public static void validaCep(String cep) {
        if (Objects.isNull(cep) || cep.strip().length() != 8 || cep.isBlank()) {
            throw new IllegalArgumentException("Cep inválido.");
        }

        for (int i = 0; i < cep.strip().length(); i++) {
            if (!Character.isDigit(cep.strip().charAt(i))) {
                throw new IllegalArgumentException("Cep inválido.");
            }
        }
    }
}
