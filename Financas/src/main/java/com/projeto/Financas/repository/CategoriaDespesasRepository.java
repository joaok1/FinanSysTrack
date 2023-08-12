package com.projeto.Financas.repository;

import com.projeto.Financas.model.CategoriaDespesas;

import com.projeto.Financas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDespesasRepository extends JpaRepository<CategoriaDespesas,Short> {
    @Query(nativeQuery = true, value = "select * from registro_categoria_despesas rc where rc.usuario = :id ORDER BY rc.name ASC")
    List<CategoriaDespesas> findAllByUsuario(Short id);
    @Query(nativeQuery = true, value = "select * from registro_categoria_despesas rc where rc.usuario = :id ORDER BY rc.name ASC ")
    Page<CategoriaDespesas> findAllByUsuarioPage(Pageable pageable, Short id);

    List<CategoriaDespesas> findByNameAndUsuario(String name, Usuario usuario);
    @Query(nativeQuery = true, value = "select * from registro_categoria_despesas rc where rc.usuario = :id ORDER BY rc.name ASC")
    List<CategoriaDespesas> findByCategoriaDespesa(Short id);
}
