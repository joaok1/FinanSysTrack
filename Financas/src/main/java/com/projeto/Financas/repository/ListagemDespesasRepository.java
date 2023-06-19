package com.projeto.Financas.repository;

import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.ListagemDespesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListagemDespesasRepository extends JpaRepository<ListagemDespesas,Short> {
}
