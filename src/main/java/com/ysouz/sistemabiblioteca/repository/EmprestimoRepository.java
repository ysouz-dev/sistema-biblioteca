package com.ysouz.sistemabiblioteca.repository;

import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.EmprestimoNaoEncontradoException;
import com.ysouz.sistemabiblioteca.exception.LivroNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.connection.Conexao;
import com.ysouz.sistemabiblioteca.dto.EmprestimoDTO;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Objects;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Repositório responsável pelos registros de empréstimos do sistema.
 */
public class EmprestimoRepository {

    /**
     * Registra um novo empréstimo no sistema, vinculando o usuário ao livro
     * informado e atualizando a disponibilidade do livro para indisponível.
     *
     * @param emprestimo empréstimo a ser registrado
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados ou ao realizar rollback da transação
     */
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

    /**
     * Realiza devolução do empréstimo pendente vinculado ao cpf informado,
     * atualizando a situação do empréstimo para devolvido e a disponibilidade
     * do livro para disponível.
     * <p>
     * A atualização do livro acontece antes da atualização do empréstimo,
     * pois depende do vínculo com um empréstimo ainda em situação 'PENDENTE'.
     * Inverter essa ordem impede a consulta de localizar o empréstimo correto.
     *
     * @param cpf cpf do usuário
     * @throws LivroNaoEncontradoException se não houver empréstimo pendente
     *          vinculado ao cpf informado
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void devolver(String cpf) {
        String queryLivro = "UPDATE livros SET disponivel = true WHERE isbn = (SELECT Isbn_livro from emprestimos WHERE" +
                " cpf_usuario = ? and situacao = 'PENDENTE')";

        String queryDevolucao = "UPDATE emprestimos SET situacao = 'DEVOLVIDO' WHERE cpf_usuario = ?" +
                                " and situacao = 'PENDENTE'";


        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            try (PreparedStatement statementDevolucao = conexao.prepareStatement(queryDevolucao);
                PreparedStatement statementLivro = conexao.prepareStatement(queryLivro)) {

                statementLivro.setString(1, cpf);
                int linhas = statementLivro.executeUpdate();

                if (linhas == 0) {
                    throw new LivroNaoEncontradoException("Livro do empréstimo não encontrado no sistema.");
                }

                statementDevolucao.setString(1,cpf);
                statementDevolucao.executeUpdate();


            }
            conexao.commit();

        } catch (Exception e) {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Erro ao realizar rollback.", ex);
                }
            }
            throw new DatabaseException("Erro ao salvar devolução no banco.", e);

        } finally {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar conexão com o banco.");
                }
            }
        }

    }

    /**
     * Busca o empréstimo pendente referente ao cpf informado.
     * <p>
     * A consulta utiliza JOIN para trazer o nome do usuário e título do livro
     * em uma query única, evitando múltiplas consultas ao banco.
     *
     * @param cpf cpf do usuário
     * @return dados do empréstimo pendente encontrado, incluindo nome do usuário e título do livro
     * @throws EmprestimoNaoEncontradoException se não houver nenhum empréstimo pendente
     *          para o cpf informado no sistema
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public EmprestimoDTO buscaEmprestimoPendentePorCpf(String cpf) {
        String query = "SELECT e.id, u.nome, l.titulo, e.data, e.situacao from emprestimos as e " +
                        "JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "JOIN livros as l " +
                        "ON l.isbn = e.isbn_livro " +
                        "WHERE e.cpf_usuario = ? and e.situacao = 'PENDENTE'";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String livro = rs.getString("titulo");
                    LocalDate data = LocalDate.parse(rs.getString("data"));
                    String situacao = rs.getString("situacao");

                    return new EmprestimoDTO(id, nome, livro, data, situacao);
                }
                throw new EmprestimoNaoEncontradoException("Empréstimo não encontrado no sistema.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar empréstimo no banco", e);
        }
    }

    /**
     * Busca todos os empréstimos do cpf informado, incluindo os pendentes e devolvidos.
     *
     * @param cpf cpf do usuário
     * @return uma lista com os dados dos empréstimos encontrados
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<EmprestimoDTO> buscaTodosEmprestimosPorCpf(String cpf) {
        String query = "SELECT e.id, u.nome, l.titulo, e.data, e.situacao from emprestimos as e " +
                        "JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "JOIN livros as l " +
                        "ON l.isbn = e.isbn_livro " +
                        "WHERE e.cpf_usuario = ? " +
                        "ORDER BY e.id";

        List<EmprestimoDTO> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String titulo = rs.getString("titulo");
                    LocalDate data = LocalDate.parse(rs.getString("data"));
                    String situacao = rs.getString("situacao");

                    lista.add(new EmprestimoDTO(id, nome, titulo, data, situacao));

                }
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar empréstimo no banco", e);
        }
    }

    /**
     * Lista todos os empréstimos registrados no sistema.
     *
     * @return uma lista com os dados de todos os empréstimos registrados
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<EmprestimoDTO> listaTodosEmprestimos() {
        String query = "SELECT e.id, u.nome, l.titulo, e.data, e.situacao FROM emprestimos as e " +
                        "JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "JOIN livros as l " +
                        "ON l.isbn = e.isbn_livro " +
                        "ORDER BY u.nome, e.data";

        List<EmprestimoDTO> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String titulo = rs.getString("titulo");
                LocalDate data = LocalDate.parse(rs.getString("data"));
                String situacao = rs.getString("situacao");

                lista.add(new EmprestimoDTO(id, nome, titulo, data, situacao));
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar lista de empréstimos no banco", e);
        }
    }

    /**
     * Lista todos os empréstimos pendentes do sistema.
     *
     * @return uma lista com os dados de todos os empréstimos pendentes do sistema
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public List<EmprestimoDTO> listaTodosEmprestimosPendentes() {
        String query = "SELECT e.id, u.nome, l.titulo, e.data, e.situacao FROM emprestimos as e " +
                        "JOIN usuarios as u " +
                        "ON u.cpf = e.cpf_usuario " +
                        "JOIN livros as l " +
                        "ON l.isbn = e.isbn_livro " +
                        "WHERE e.situacao = 'PENDENTE' " +
                        "ORDER BY u.nome";

        List<EmprestimoDTO> lista = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String titulo = rs.getString("titulo");
                LocalDate data = LocalDate.parse(rs.getString("data"));
                String situacao = rs.getString("situacao");

                lista.add(new EmprestimoDTO(id, nome, titulo, data, situacao));
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar lista de empréstimos pendentes no banco", e);
        }
    }

    /**
     * Verifica se existe um empréstimo pendente com o cpf informado.
     *
     * @param cpf cpf do usuário
     * @return true se existir um empréstimo pendente para o cpf informado, false caso o contrário
     * @throws DatabaseException se ocorrer erro ao acessar o banco de dados
     */
    public boolean containsEmprestimo(String cpf) {
        String query = "SELECT 1 FROM emprestimos WHERE cpf_usuario = ? and situacao = 'PENDENTE'";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)){

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar se existe empréstimo no banco", e);
        }
    }
}
