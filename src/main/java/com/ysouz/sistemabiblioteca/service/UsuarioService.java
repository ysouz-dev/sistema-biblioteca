package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.UsuarioJaCadastradoException;
import com.ysouz.sistemabiblioteca.repository.UsuarioRepository;
import com.ysouz.sistemabiblioteca.model.Usuario;


public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (this.usuarioRepository.containsUsuario(usuario.getCpf())) {
            throw new UsuarioJaCadastradoException("Usuário com esse cpf já cadastrado no sistema.");
        }
        this.usuarioRepository.salvar(usuario);
    }
}
