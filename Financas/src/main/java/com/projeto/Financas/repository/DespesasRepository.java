package com.projeto.Financas.repository;

import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesasRepository  extends JpaRepository<Despesas, Short> {
    @Query(nativeQuery = true, value = "select * from despesas where usuario = :id")
    Page<Despesas> findByUsuario(Short id, Pageable pageable);
}
