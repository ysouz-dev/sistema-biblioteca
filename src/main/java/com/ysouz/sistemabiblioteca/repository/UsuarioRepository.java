package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.model.Usuario;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;

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
}
