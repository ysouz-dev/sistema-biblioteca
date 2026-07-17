package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
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

    public Livro buscaPorIsbn(String isbn) {
        String query = "SELECT * FROM livros WHERE isbn = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, isbn);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String ISBN = rs.getString("isbn");
                    int anoLancamento = rs.getInt("ano_lancamento");
                    boolean disponivel = rs.getBoolean("disponivel");

                    return new Livro(titulo, autor, ISBN, anoLancamento, disponivel);
                }
                throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Livro no banco", e);
        }
    }
}
