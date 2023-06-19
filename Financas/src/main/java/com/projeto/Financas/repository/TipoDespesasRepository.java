package com.projeto.Financas.repository;

import com.projeto.Financas.model.TipoDespesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespesasRepository extends JpaRepository<TipoDespesas,Short> {
}
