package com.ysouz.sistemabiblioteca;

import com.ysouz.sistemabiblioteca.controller.EmprestimoController;
import com.ysouz.sistemabiblioteca.controller.LivroController;
import com.ysouz.sistemabiblioteca.controller.MenuController;
import com.ysouz.sistemabiblioteca.controller.UsuarioController;
import com.ysouz.sistemabiblioteca.repository.EmprestimoRepository;
import com.ysouz.sistemabiblioteca.repository.LivroRepository;
import com.ysouz.sistemabiblioteca.repository.UsuarioRepository;
import com.ysouz.sistemabiblioteca.service.EmprestimoService;
import com.ysouz.sistemabiblioteca.service.LivroService;
import com.ysouz.sistemabiblioteca.service.UsuarioService;

import java.util.Scanner;


public class Main {
    public static MenuController createMenu() {
        Scanner scanner = new Scanner(System.in);
        LivroRepository livroRepository = new LivroRepository();
        LivroService livroService = new LivroService(livroRepository);
        LivroController livroController = new LivroController(scanner, livroService);

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(scanner, usuarioService);

        EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
        EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepository);
        EmprestimoController emprestimoController = new EmprestimoController(scanner, emprestimoService,
                usuarioService, livroService);

        return new MenuController(livroController, usuarioController, emprestimoController, scanner);
    }

    public static void main(String[] args) {
        MenuController menu = createMenu();
        menu.iniciar();
    }
}