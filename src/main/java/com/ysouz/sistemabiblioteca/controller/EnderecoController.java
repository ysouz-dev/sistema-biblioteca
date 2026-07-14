package com.ysouz.sistemabiblioteca.controller;

import com.ysouz.sistemabiblioteca.model.Endereco;
import com.ysouz.sistemabiblioteca.validation.EnderecoValidator;

import java.util.Scanner;

public class EnderecoController {
    private Scanner scanner;

    public EnderecoController(Scanner scanner) {
        this.scanner = scanner;
    }

    public Endereco cadastrarEndereco() {

        String rua = "";
        String bairro = "";
        String numero = "";
        String cep = "";

        int contador = 1;

        while (true) {
            try {
                if (contador == 1) {
                    System.out.print("Rua: ");
                    rua = this.scanner.nextLine();
                    EnderecoValidator.validaLogradouro(rua, "rua");
                    contador++;
                }

                if (contador == 2) {
                    System.out.print("Bairro: ");
                    bairro = this.scanner.nextLine();
                    EnderecoValidator.validaLogradouro(bairro, "bairro");
                    contador++;
                }

                if (contador == 3) {
                    System.out.print("Número");
                    numero = this.scanner.nextLine();
                    EnderecoValidator.validaLogradouro(numero, "número");
                    contador++;
                }

                if (contador == 4) {
                    System.out.print("CEP: ");
                    cep = this.scanner.nextLine();
                    EnderecoValidator.validaCep(cep);
                    contador++;
                }

                return new Endereco(rua, numero, bairro, cep);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
