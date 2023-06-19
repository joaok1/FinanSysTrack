package com.projeto.Financas.repository;

import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesasRepository  extends JpaRepository<Despesas, Short> {
}
