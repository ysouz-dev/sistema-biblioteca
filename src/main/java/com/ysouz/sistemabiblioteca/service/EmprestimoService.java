package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.exception.EmprestimoNaoEncontradoException;
import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.dto.EmprestimoDTO;
import com.ysouz.sistemabiblioteca.repository.EmprestimoRepository;
import com.ysouz.sistemabiblioteca.exception.EmprestimoPendenteException;

import java.util.List;

public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService() {
        this.emprestimoRepository = new EmprestimoRepository();
    }

    public void cadastrarEmprestimo(Emprestimo emprestimo) {
        if (this.emprestimoRepository.containsEmprestimo(emprestimo.getUsuario().getCpf())) {
            throw new EmprestimoPendenteException("O usuário já possui um empréstimo pendente.");
        }
        this.emprestimoRepository.emprestar(emprestimo);
    }

    public void devolverLivro(String cpf) {
        if (!this.emprestimoRepository.containsEmprestimo(cpf)) {
            throw new EmprestimoNaoEncontradoException("Empréstimo não encontrado no sistema.");
        }
        this.emprestimoRepository.devolver(cpf);
    }

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
}
