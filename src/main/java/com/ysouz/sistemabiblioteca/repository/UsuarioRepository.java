package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.dto.UsuarioDTO;
import com.ysouz.sistemabiblioteca.exception.DadoInconsistenteException;
import com.ysouz.sistemabiblioteca.model.Endereco;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.UsuarioNaoEncontradoException;
import com.ysouz.sistemabiblioteca.enums.Sexo;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Repositório responsável pelos registros dos usuários no sistema.
 */
public class UsuarioRepository {

    /**
     * Resgitra um novo usuário no sistema, persistindo os seus dados
     * e o seu endereço vinculado.
     * <p>
     * O endereço é salvo na mesma transação por ser tratado como parte
     * inseparável do usuário (não existe existência própria de domínio)
     *
     * @param usuario usuário a ser salvo, incluindo o seu endereço
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void salvar(Usuario usuario) {
        String queryUsuario = "INSERT INTO usuarios(nome, cpf, sexo) values (?, ?, ?)";
        String queryEndereco = "INSERT INTO enderecos(cpf_usuario, rua, bairro, numero, cep) values (?, ?, ?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            try (PreparedStatement statementEndereco = conexao.prepareStatement(queryEndereco);
                PreparedStatement statementUsuario = conexao.prepareStatement(queryUsuario)) {

                statementUsuario.setString(1, usuario.getNome());
                statementUsuario.setString(2, usuario.getCpf());
                statementUsuario.setString(3, usuario.getSexo().getSigla());
                statementUsuario.executeUpdate();

                statementEndereco.setString(1, usuario.getCpf());
                statementEndereco.setString(2, usuario.getEndereco().getRua());
                statementEndereco.setString(3, usuario.getEndereco().getBairro());
                statementEndereco.setString(4, usuario.getEndereco().getNumero());
                statementEndereco.setString(5, usuario.getEndereco().getCep());
                statementEndereco.executeUpdate();
            }
            conexao.commit();

        } catch (Exception e) {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Erro ao realizar rollback", ex);
                }
            }
            throw new DatabaseException("Erro ao salvar usuário", e);

        } finally {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão com o banco" + e.getMessage());
                }
            }
        }
    }

    /**
     * Verifica se existe um usuário no sistema com o cpf informado.
     *
     * @param cpf cpf do usuário
     * @return true se possuir um usuário com o cpf informado, false caso o contrário
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public boolean containsUsuario(String cpf) {
        String query = "SELECT 1 FROM usuarios WHERE cpf = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)){

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar se existe usuário no banco", e);
        }
    }

    /**
     * Busca o usuário referente ao cpf informado.
     * <p>
     * A consulta utiliza RIGHT JOIN a partir de {@code enderecos}, garantindo
     * que o usuário seja retornado mesmo sem endereço cadastrado, permitindo
     * encontrar alguma inconsistência em vez de simplesmente omitir o registro.
     *
     * @param cpf cpf do usuário
     * @return o usuário encontrado
     * @throws DadoInconsistenteException se o usuário encontrado possuir o endereço nulo
     * @throws UsuarioNaoEncontradoException se nenhum usuário for encontrado com o cpf informado
     * @throws DatabaseException se ocorrer um erro ao
     */
    public Usuario buscaPorCpf(String cpf) {
        String query = "SELECT u.*, e.rua, e.bairro, e.numero, e.cep FROM enderecos as e " +
                        "RIGHT JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "WHERE u.cpf = ?";

        try (Connection conexao =   Conexao.getConexao();
             PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                        String rua = rs.getString("rua");
                        String bairro = rs.getString("bairro");
                        String numero = rs.getString("numero");
                        String cep = rs.getString("cep");

                    if (rua != null && bairro != null && numero != null && cep != null) {
                        String nome = rs.getString("nome");
                        String CPF = rs.getString("cpf");
                        Sexo sexo = Sexo.toSexo(rs.getString("sexo"));

                        return new Usuario(nome, CPF, sexo, new Endereco(rua, numero, bairro, cep));

                    }
                    throw new DadoInconsistenteException("Endereço do usuário não encontrado no sistema.");
                }
                throw new UsuarioNaoEncontradoException("Usuário não encontrado no sistema.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário no banco", e);
        }
    }

    /**
     * Lista dos usuários encontrados referente ao nome informado (busca parcial).
     * <p>
     * Caso algum usuário que seja encontrado não possua endereço cadastrado,
     * uma inconsistencia de dados é lançada, já que o endereço é obrigatório
     * para os usuários do sistema.
     * <p>
     * A consulta utiliza RIGHT JOIN a partir de {@code enderecos}, garantindo
     * que o usuário seja retornado mesmo sem endereço cadastrado, permitindo
     * encontrar alguma inconsistência em vez de simplesmente omitir o registro.
     *
     * @param nome nome do usuário
     * @return uma lista dos usuários encontrados
     * @throws DadoInconsistenteException se o usuário encontrado possuir o endereço nulo
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<Usuario> buscaPorNome(String nome) {
        String query = "SELECT u.*, e.rua, e.bairro, e.numero, e.cep FROM enderecos as e " +
                "RIGHT JOIN usuarios as u " +
                "ON u.cpf = e.cpf_usuario " +
                "WHERE u.nome like ? " +
                "ORDER BY u.nome";

        List<Usuario> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, "%" + nome + "%");


            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String rua = rs.getString("rua");
                    String bairro = rs.getString("bairro");
                    String numero = rs.getString("numero");
                    String cep = rs.getString("cep");

                    if (rua != null && bairro != null && numero != null && cep != null) {
                        String NOME = rs.getString("nome");
                        String cpf = rs.getString("cpf");
                        Sexo sexo = Sexo.toSexo(rs.getString("sexo"));

                        lista.add(new Usuario(NOME, cpf, sexo, new Endereco(rua, numero, bairro, cep)));

                    } else {
                        String usuario = rs.getString("nome");
                        throw new DadoInconsistenteException("Endereço do usuário (" + usuario
                                + ") não encontrado no sistema.");
                    }
                }
            }

            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário no banco", e);
        }
    }

    /**
     * Lista todos os usuários do sistema.
     *
     * @return uma lista com todos os usuários registrados
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<UsuarioDTO> listaUsuarios() {
        String query = "SELECT * FROM usuarios ORDER BY nome";

        List<UsuarioDTO> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            while(rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Sexo sexo = Sexo.toSexo(rs.getString("sexo"));

                lista.add(new UsuarioDTO(nome, cpf, sexo));
            }

            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar lista de usuários no banco", e);
        }
    }

    /**
     * Lista todos os usuários com empréstimos pendentes.
     * <p>
     * A consulta utiliza JOIN com a tabela {@code emprestimos} para filtrar
     * apenas usuários que possuam algum empréstimo com situação 'PENDENTE',
     * já que essa informação não está disponível na tabela {@code usuarios}
     *
     * @return uma lista dos usuários com empréstimos pendentes
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<UsuarioDTO> listaUsuariosPendentes() {
        String query = "SELECT u.* FROM emprestimos as e " +
                        "JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "WHERE e.situacao = 'PENDENTE' " +
                        "ORDER BY u.nome";

        List<UsuarioDTO> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Sexo sexo = Sexo.toSexo(rs.getString("sexo"));

                lista.add(new UsuarioDTO(nome, cpf, sexo));
            }

            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar lista usuários no banco", e);
        }
    }
}
