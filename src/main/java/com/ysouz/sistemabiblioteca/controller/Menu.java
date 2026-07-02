package com.ysouz.sistemabiblioteca.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static int menuPrincipal(Scanner scanner) {
        String titulo = "Biblioteca YS";
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("| " + titulo + " |");
        System.out.println("+ " + "-".repeat(titulo.length()) + " +");
        System.out.println("[ 1 ] Cadastrar Livro");
        System.out.println("[ 0 ] Encerrar Sistema");
        System.out.println("-".repeat(20));

        int resposta = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite uma opção: ");
                resposta = scanner.nextInt();
                scanner.nextLine();
                if (resposta < 0 || resposta > 1) {
                    System.out.printf("Opção inválida! %d não é uma opção.%n", resposta);
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite somente números.");
                scanner.nextLine();
            }
        } while(resposta < 0 || resposta > 1);
        return resposta;
    }

    public static void encerrarSistema(Scanner scanner) {
        System.out.println("Sistema encerrado, volte sempre!");
        scanner.close();
    }
}
