package com.projeto.Financas.repository;

import com.projeto.Financas.model.CategoriaDespesas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDespesasRepository extends JpaRepository<CategoriaDespesas,Short> {
    @Query(nativeQuery = true, value = "select * from registro_categoria_despesas where usuario = :id")
    List<CategoriaDespesas> findAllByUsuario(Short id);
    @Query(nativeQuery = true, value = "select * from registro_categoria_despesas where usuario = :id")
    Page<CategoriaDespesas> findAllByUsuarioPage(Pageable pageable, Short id);

    @Query(nativeQuery = true, value = "SELECT * FROM registro_categoria_despesas rcd WHERE rcd.name = UPPER(:name) OR rcd.name = LOWER(:name)")
    List<CategoriaDespesas> findByName(String name);
}
