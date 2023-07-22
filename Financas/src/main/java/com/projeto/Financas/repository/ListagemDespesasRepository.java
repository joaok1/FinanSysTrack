package com.projeto.Financas.repository;

import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.ListagemDespesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListagemDespesasRepository extends JpaRepository<ListagemDespesas,Short> {
    void deleteByDespesas(Despesas despesas);

    List<ListagemDespesas> findBydespesasCategory(CategoriaDespesas categoriaDespesas);
}
