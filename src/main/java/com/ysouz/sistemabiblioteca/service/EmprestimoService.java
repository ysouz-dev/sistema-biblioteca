package com.ysouz.sistemabiblioteca.service;

import com.ysouz.sistemabiblioteca.model.Emprestimo;
import com.ysouz.sistemabiblioteca.repository.EmprestimoRepository;
import com.ysouz.sistemabiblioteca.exception.EmprestimoPendenteException;

public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService() {
        this.emprestimoRepository = new EmprestimoRepository();
    }

    public void cadastrarEmprestimo(Emprestimo emprestimo) {
        if (this.emprestimoRepository.containsEmprestimo(emprestimo)) {
            throw new EmprestimoPendenteException("O usuário já possui um empréstimo pendente.");
        }
        this.emprestimoRepository.emprestar(emprestimo);
    }
}
