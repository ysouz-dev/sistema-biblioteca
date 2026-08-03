package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.DatabaseException;
import com.ysouz.sistemabiblioteca.exception.EmprestimoNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.dto.EmprestimoDTO;
import com.ysouz.sistemabiblioteca.repository.EmprestimoRepository;
import com.ysouz.sistemabiblioteca.exception.EmprestimoPendenteException;

import java.util.List;

/**
 * Serviço responsável pelas regras de negócios relacionadas aos empréstimos,
 * incluindo cadastro, busca, listagem e validação de integridade dos dados.
 */
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService() {
        this.emprestimoRepository = new EmprestimoRepository();
    }

    /**
     * Cadastra o empréstimo informado no sistema.
     *
     * @param emprestimo empréstimo a ser cadastrado no sistema
     * @throws EmprestimoPendenteException se o usuário solicitante do empréstimo já possuir um empréstimo pendente
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void cadastrarEmprestimo(Emprestimo emprestimo) {
        if (this.emprestimoRepository.containsEmprestimo(emprestimo.getUsuario().getCpf())) {
            throw new EmprestimoPendenteException("O usuário já possui um empréstimo pendente.");
        }
        this.emprestimoRepository.emprestar(emprestimo);
    }

    /**
     * Realiza devolução do livro emprestado referente ao cpf informado.
     *
     * @param cpf cpf do usuário que realizou o empréstimo
     * @throws EmprestimoNaoEncontradoException se o empréstimo referente ao cpf informado não for encontrado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void devolverLivro(String cpf) {
        if (!this.emprestimoRepository.containsEmprestimo(cpf)) {
            throw new EmprestimoNaoEncontradoException("Empréstimo não encontrado no sistema.");
        }
        this.emprestimoRepository.devolver(cpf);
    }

    /**
     * Busca o empréstimo pendente referente ao cpf informado.
     *
     * @param cpf cpf do usuário que realizou o empréstimo
     * @return empréstimo pendente encontrado referente ao cpf informado
     * @throws EmprestimoNaoEncontradoException se nenhum empréstimo for encontrado referente ao cpf informado
     * @throws DatabaseException se ocorrer erro ao acessar banco de dados
     */
    public EmprestimoDTO buscaEmprestimoPendentePorCpf(String cpf) {
        return this.emprestimoRepository.buscaEmprestimoPendentePorCpf(cpf);
    }

    public List<EmprestimoDTO> buscaTodosEmprestimosPorCpf(String cpf) {
        List<EmprestimoDTO> lista = this.emprestimoRepository.buscaTodosEmprestimosPorCpf(cpf);
        if (lista.isEmpty()) {
            throw new EmprestimoNaoEncontradoException("Não há nenhum empréstimo registrado para esse cpf.");
        }
        return lista;
    }

    /**
     * Lista todos os empréstimos do sistema.
     *
     * @return uma lista de todos os empréstimos registrados no sistema
     * @throws EmprestimoNaoEncontradoException se o sistema não possuir nenhum empréstimo registrado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<EmprestimoDTO> listaTodosEmprestimos() {
        List<EmprestimoDTO> lista = this.emprestimoRepository.listaTodosEmprestimos();
        if (lista.isEmpty()) {
            throw new EmprestimoNaoEncontradoException("Náo há nenhum empréstimo registrado no sistema.");
        }
        return lista;
    }

    /**
     * Lista todos os empréstimos pendentes do sistema.
     *
     * @return uma lista de todos os empréstimos pendentes do sistema.
     * @throws EmprestimoNaoEncontradoException se o sistema não possuir nenhum empréstimo pendente
     * @throws DatabaseException se ocorrer um errro ao acessar o banco de dados
     */
    public List<EmprestimoDTO> listaTodosEmprestimosPendentes() {
        List<EmprestimoDTO> lista = this.emprestimoRepository.listaTodosEmprestimosPendentes();
        if (lista.isEmpty()) {
            throw new EmprestimoNaoEncontradoException("Não há nenhum empréstimo pendente no sistema.");
        }
        return lista;
    }
}
