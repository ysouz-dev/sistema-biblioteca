package com.ysouz.sistemabiblioteca;

import com.ysouz.sistemabiblioteca.controller.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        int resposta = Integer.MIN_VALUE;
        while(resposta != 0) {
            resposta = menu.menuPrincipal();

            switch(resposta) {
                case 1:
                    menu.cadastrarLivro();
                    break;

                case 2:
                    menu.cadastrarUsuario();
                    break;

                case 0:
                    menu.encerrarSistema();
                    break;
            }
        }
    }
}