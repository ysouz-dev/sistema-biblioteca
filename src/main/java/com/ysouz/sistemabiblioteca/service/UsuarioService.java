package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.UsuarioJaCadastradoException;
import com.ysouz.sistemabiblioteca.exception.UsuarioNaoEncontradoException;
import com.ysouz.sistemabiblioteca.repository.UsuarioRepository;
import com.ysouz.sistemabiblioteca.model.Usuario;

import java.util.List;


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

    public Usuario buscarUsuarioPorCpf(String cpf) {
        return this.usuarioRepository.buscaPorCpf(cpf);
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) {
        List<Usuario> lista = this.usuarioRepository.buscaPorNome(nome);
        if (lista.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não há nenhum usuário com esse nome.");
        }
        return lista;
    }

    public List<Usuario> listaUsuariosPendente() {
        List<Usuario> lista = this.usuarioRepository.listaUsuariosPendentes();
        if (lista.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não há nenhum usuário com empréstimo pendente");
        }
        return lista;
    }
}
