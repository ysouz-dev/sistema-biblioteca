package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.model.Endereco;
import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.EnderecoNaoEncontradoException;
import com.ysouz.sistemabiblioteca.exception.UsuarioNaoEncontradoException;
import com.ysouz.sistemabiblioteca.enums.Sexo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class UsuarioRepository {

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

    public Usuario buscaPorCpf(String cpf) {
        String queryUsuario = "SELECT * FROM usuarios WHERE cpf = ?";
        String queryEndereco = "SELECT * FROM enderecos WHERE cpf_usuario = ?";

        try (Connection conexao =   Conexao.getConexao();
             PreparedStatement statementUsuario = conexao.prepareStatement(queryUsuario);
            PreparedStatement statementEndereco = conexao.prepareStatement(queryEndereco)) {

            statementUsuario.setString(1, cpf);
            statementEndereco.setString(1, cpf);

            try (ResultSet rsUsuario = statementUsuario.executeQuery();
                ResultSet rsEndereco = statementEndereco.executeQuery()) {

                if (rsUsuario.next()) {
                    if (rsEndereco.next()) {
                        String nome = rsUsuario.getString("nome");
                        String CPF = rsUsuario.getString("cpf");
                        Sexo sexo = Sexo.toSexo(rsUsuario.getString("sexo"));

                        String rua = rsEndereco.getString("rua");
                        String bairro = rsEndereco.getString("bairro");
                        String numero = rsEndereco.getString("numero");
                        String cep = rsEndereco.getString("cep");

                        return new Usuario(nome, CPF, sexo, new Endereco(rua, numero, bairro, cep));

                    }
                    throw new EnderecoNaoEncontradoException("Endereço do usuário não encontrado no sistema.");
                }
                throw new UsuarioNaoEncontradoException("Usuário não encontrado no sistema.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário no banco", e);
        }
    }
}
