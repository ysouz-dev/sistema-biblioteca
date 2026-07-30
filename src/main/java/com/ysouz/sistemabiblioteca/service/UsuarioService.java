package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.dto.UsuarioDTO;
import com.ysouz.sistemabiblioteca.exception.DadoInconsistenteException;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.UsuarioJaCadastradoException;
import com.ysouz.sistemabiblioteca.exception.UsuarioNaoEncontradoException;
import com.ysouz.sistemabiblioteca.repository.UsuarioRepository;
import com.ysouz.sistemabiblioteca.model.Usuario;

import java.util.List;

/**
 * Serviço responsável pelas regras de negócios relacionadas aos usuários,
 * incluindo cadastro, busca, listagem e validação de integridade dos dados
 * (com obrigatoriedade de endereço).
 */
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    /**
     * Cadastra o usuário informado no sistema.
     *
     * @param usuario usuário a ser cadastrado no sistema
     * @throws UsuarioJaCadastradoException se o sistema já possuir um cadastro com o mesmo CPF
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados ou erro ao realizar rollback de transação
     */
    public void cadastrarUsuario(Usuario usuario) {
        if (this.usuarioRepository.containsUsuario(usuario.getCpf())) {
            throw new UsuarioJaCadastradoException("Usuário com esse cpf já cadastrado no sistema.");
        }
        this.usuarioRepository.salvar(usuario);
    }

    /**
     *
     * Busca usuário de acordo com o cpf informado.
     * <p>
     * Caso algum usuário que seja encontrado não possua endereço cadastrado,
     * uma inconsistencia de dados é lançada, já que o endereço é obrigatório
     * para os usuários do sistema.
     *
     * @param cpf CPF do usuário a ser buscado
     * @return Usuário encontrado referente à busca
     * @throws UsuarioNaoEncontradoException se nenhum usuário for encontrado com o cpf informado
     * @throws DadoInconsistenteException se o usuário retornado não tiver endereço
     * @throws DatabaseException Se ocorrer erro ao acessar banco de dados
     */
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return this.usuarioRepository.buscaPorCpf(cpf);
    }

    /**
     * Busca usuário de acordo com o nome informado (busca parcial)
     * <p>
     * Caso algum usuário que seja encontrado não possua endereço cadastrado,
     * uma inconsistencia de dados é lançada, já que o endereço é obrigatório
     * para os usuários do sistema.
     *
     * @param nome nome do usuário a ser buscado
     * @return uma lista dos usuários encontrados
     * @throws UsuarioNaoEncontradoException se não houver nenhum usuário com o nome informado
     * @throws DadoInconsistenteException se algum usuário retornado não tiver endereço
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados
     */
    public List<Usuario> buscarUsuarioPorNome(String nome) {
        List<Usuario> lista = this.usuarioRepository.buscaPorNome(nome);
        if (lista.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não há nenhum usuário com esse nome.");
        }
        return lista;
    }

    /**
     * Lista todos os usuários ativos no sistema.
     *
     * @return uma lista dos usuários ativos cadastrados no sistema
     * @throws UsuarioNaoEncontradoException se não houver nenhum usuário cadastrado no sistema
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados
     */
    public List<UsuarioDTO> listaUsuarios() {
        List<UsuarioDTO> lista = this.usuarioRepository.listaUsuarios();
        if (lista.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não há nenhum usuário cadastrado no sistema.");
        }
        return lista;
    }

    /**
     * Lista todos os usuários com empréstimos pendentes no sistema.
     *
     * @return uma lista dos usuários com empréstimos pendentes
     * @throws UsuarioNaoEncontradoException se não houver nenhum usuário com empréstimo pendente
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados
     */
    public List<UsuarioDTO> listaUsuariosPendente() {
        List<UsuarioDTO> lista = this.usuarioRepository.listaUsuariosPendentes();
        if (lista.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não há nenhum usuário com empréstimo pendente");
        }
        return lista;
    }
}
