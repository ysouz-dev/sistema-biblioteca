package com.ysouz.sistemabiblioteca.validation;

import java.util.Objects;
import java.time.LocalDate;

public final class LivroValidator {

    public static void validaTitulo(String titulo) {
        if (Objects.isNull(titulo) || titulo.strip().length() <= 3 || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido.");
        }
    }

    public static void validaAutor(String autor) {
        if (Objects.isNull(autor) || autor.strip().length() < 3 || autor.isBlank()) {
            throw new IllegalArgumentException("Autor Inválido.");
        }

        String autorSemEspaco = autor.replace(" ", "");
        for (int i = 0; i < autorSemEspaco.length(); i++) {
            if (!Character.isLetter(autorSemEspaco.charAt(i))) {
                throw new IllegalArgumentException("Autor Inválido.");
            }
        }
    }

    public static void validaIsbn(String isbn) {
        if (Objects.isNull(isbn) || isbn.isBlank() || isbn.strip().length() != 13) {
            throw new IllegalArgumentException("ISBN inválido.");
        }

        String isbnSemEspaco = isbn.replace(" ", "");
        for (int i = 0; i < isbnSemEspaco.length(); i++) {
            if (!Character.isDigit(isbnSemEspaco.charAt(i))) {
                throw new IllegalArgumentException("ISBN inválido.");
            }
        }
    }

    public static void validaAnoLancamento(int ano) throws IllegalArgumentException {
        if (ano > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Ano de lançamento inválido.");
        }
    }
}
