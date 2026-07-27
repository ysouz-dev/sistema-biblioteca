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

                case 3:
                    menu.registrarEmprestimo();
                    break;

                case 4:
                    menu.devolverLivro();
                    break;

                case 5:
                    int opcao = menu.menuLivro();

                    switch (opcao) {
                        case 1:
                            menu.buscarPorTitulo();
                            break;

                        case 2:
                            menu.buscarPorIsbn();
                            break;

                        case 3:
                            menu.buscarPorAutor();
                            break;

                        case 4:
                            menu.listaLivrosDisponiveis();
                            break;

                        case 5:
                            menu.listaLivrosPendentes();
                            break;

                        case 0:
                            break;
                    }
                    break;

                case 6:
                    int opcao2 = menu.menuUsuario();

                    switch (opcao2) {
                        case 1:
                            menu.buscaPorCpf();
                            break;

                        case 2:
                            menu.buscaPorNome();
                            break;

                        case 3:
                            menu.listaUsuariosPendentes();
                            break;

                        case 0:
                            break;
                    }
                    break;

                case 0:
                    menu.encerrarSistema();
                    break;
            }
        }
    }
}