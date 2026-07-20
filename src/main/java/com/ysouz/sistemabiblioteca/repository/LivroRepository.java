package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class LivroRepository {

    public void salvar(Livro livro) {
        String query = "INSERT INTO livros VALUES ( ?, ?, ?, ?, ?)";

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

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

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

    public List<Livro> buscaPorTitulo(String titulo) {
        String query = "SELECT * FROM livros WHERE titulo = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, titulo);

            try (ResultSet rs = statement.executeQuery()) {
                List<Livro> lista = new ArrayList<>();
                while(rs.next()) {
                    String TITULO = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String isbn = rs.getString("isbn");
                    int ano = rs.getInt("ano_lancamento");
                    boolean disponivel = rs.getBoolean("disponivel");

                    lista.add(new Livro(TITULO, autor, isbn, ano, disponivel));
                }
                if (lista.isEmpty()) {
                    throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
                }
                return lista;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Livro no banco", e);
        }
    }

    public List<Livro> buscaPorAutor(String autor) {
        String query = "SELECT * FROM livros WHERE autor = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, autor);

            try (ResultSet rs = statement.executeQuery()) {
                List<Livro> lista = new ArrayList<>();
                while(rs.next()) {
                    String titulo = rs.getString("titulo");
                    String AUTOR = rs.getString("autor");
                    String isbn = rs.getString("isbn");
                    int ano = rs.getInt("ano_lancamento");
                    boolean disponivel = rs.getBoolean("disponivel");

                    lista.add(new Livro(titulo, AUTOR, isbn, ano, disponivel));
                }
                if (lista.isEmpty()){
                    throw new LivroNaoEncontradoException("Livro não encontrado no sistema.");
                }
                return lista;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Livro no banco", e);
        }
    }

    public List<Livro> livrosDisponiveis() {
        String query = "SELECT * FROM livros WHERE disponivel = true";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            List<Livro> lista = new ArrayList<>();
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String isbn = rs.getString("isbn");
                int ano = rs.getInt("ano_lancamento");
                boolean disponivel = rs.getBoolean("disponivel");

                lista.add(new Livro(titulo, autor, isbn, ano, disponivel));
            }
            if (lista.isEmpty()) {
                throw new LivroNaoEncontradoException("Não há livros disponíveis para empréstimo no momento");
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar livros disponíveis no banco", e);
        }
    }
}
