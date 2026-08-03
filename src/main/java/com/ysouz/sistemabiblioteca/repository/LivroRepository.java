package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Livro;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.exception.DatabaseException;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Repositório responsável pelos registros dos livros no sistema.
 */
public class LivroRepository {

    /**
     * Registra um novo livro no sistema.
     *
     * @param livro livro a ser registrado
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
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

    /**
     * Verifica se existe um livro com o isbn informado no sistema.
     *
     * @param isbn código isbn do livro
     * @return true se existir um livro com o isbn informado, false caso o contrário
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
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

    /**
     * Busca um livro no sistema referente ao isbn informado.
     *
     * @param isbn código isbn do livro
     * @return o livro encontrado referente a busca pelo código isbn
     * @throws LivroNaoEncontradoException se nenhum livro for encontrado com o isbn informado
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
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

    /**
     * Busca os livros conforme o título informado.
     *
     * @param titulo título dos livros a serem buscados
     * @return uma lista dos livros encontrados referente a busca pelo título
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<Livro> buscaPorTitulo(String titulo) {
        String query = "SELECT * FROM livros WHERE titulo = ? " +
                        "ORDER BY titulo";

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
                return lista;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Livro no banco", e);
        }
    }

    /**
     * Busca os livros conforme o autor informado.
     *
     * @param autor nome do autor
     * @return uma lista dos livros encontrados referente a busca pelo nome do autor
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<Livro> buscaPorAutor(String autor) {
        String query = "SELECT * FROM livros WHERE autor = ? " +
                        "ORDER BY titulo";

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
                return lista;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Livro no banco", e);
        }
    }

    /**
     * Lista todos os livros disponíveis para empréstimo.
     *
     * @return uma lista dos livros disponíveis
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<Livro> livrosDisponiveis() {
        String query = "SELECT * FROM livros WHERE disponivel = true " +
                        "ORDER BY titulo";

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
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar livros disponíveis no banco", e);
        }
    }

    /**
     * Lista todos os livros emprestados.
     *
     * @return uma lista dos livros emprestados
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<Livro> livrosPendentes() {
        String query = "SELECT * FROM livros WHERE disponivel = false " +
                        "ORDER BY titulo";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            List<Livro> lista = new ArrayList<>();
            while(rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String isbn = rs.getString("isbn");
                int ano = rs.getInt("ano_lancamento");
                boolean disponivel = rs.getBoolean("disponivel");

                lista.add(new Livro(titulo, autor, isbn, ano, disponivel));
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar livros pendentes no banco", e);
        }
    }
}
