package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.connection.Conexao;

import java.util.Objects;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class EmprestimoRepository {

    public void emprestar(Emprestimo emprestimo) {
        String queryEmprestimo = "INSERT INTO emprestimos VALUES (default, ?, ?, ?, default)";
        String queryLivro = "UPDATE livros SET disponivel = false WHERE isbn = ?";

        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            try (PreparedStatement statementEmprestimo = conexao.prepareStatement(queryEmprestimo);
                PreparedStatement statementLivro = conexao.prepareStatement(queryLivro)) {

                statementEmprestimo.setString(1, emprestimo.getUsuario().getCpf());
                statementEmprestimo.setString(2, emprestimo.getLivro().getIsbn());
                statementEmprestimo.setString(3, emprestimo.getData().toString());
                statementEmprestimo.executeUpdate();

                statementLivro.setString(1, emprestimo.getLivro().getIsbn());
                statementLivro.executeUpdate();
            }
            conexao.commit();

        }catch (Exception e) {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Erro ao realizar rollback", ex);
                }
            }
            throw new DatabaseException("Erro ao salvar empréstimo no banco", e);
        } finally {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar conexão com o banco: " + ex.getMessage());
                }
            }
        }
    }
}
