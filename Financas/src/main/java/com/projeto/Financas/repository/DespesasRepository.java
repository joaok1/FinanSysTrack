package com.projeto.Financas.repository;

import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesasRepository  extends JpaRepository<Despesas, Short> {

    Page<Despesas> findAllByUsuario(Pageable pageable, Usuario usuario);
}
