package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class LivroRepository {

    public void salvar(Livro livro) {
        String query = "INSERT INTO livros VALUES (default , ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getAutor());
            statement.setString(3, livro.getIsbn());
            statement.setInt(4, livro.getAnoLancamento());
            statement.setBoolean(5, livro.isDisponivel());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar livro.", e);
        }
    }

    public boolean containsLivro(String isbn) {
        String query = "SELECT 1 FROM livros WHERE isbn = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, isbn);
            ResultSet rs = statement.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar existência de livro no banco.", e);
        }
    }
}
