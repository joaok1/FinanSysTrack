package com.projeto.Financas.repository;

import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.ListagemDespesas;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ListagemDespesasRepository extends JpaRepository<ListagemDespesas,Short> {
    void deleteByDespesas(Despesas despesas);

    List<ListagemDespesas> findBydespesasCategory(CategoriaDespesas categoriaDespesas);

    @Query(nativeQuery = true, value = "SELECT SUM(valor) AS total_despesas ,rtd.name AS tipo " +
            "FROM listagem_despesas ld " +
            "INNER JOIN registro_categoria_despesas rcd  " +
            "on ld.registro_categoria_despesas_id = rcd.id " +
            "INNER JOIN registro_tipo_despesas rtd " +
            "ON rcd.tipo = rtd.id " +
            "WHERE ld.despesas_id = :id " +
            "GROUP BY rtd.name;")
    List<Object> findAllTipoDespesas(Short id);
    @Query(nativeQuery = true, value ="SELECT rcd.name as categoria, rtd.name as tipo, ld.valor " +
            "from listagem_despesas ld " +
            "inner join registro_categoria_despesas rcd " +
            "on ld.registro_categoria_despesas_id = rcd.id " +
            "inner join registro_tipo_despesas rtd " +
            "on rcd.tipo = rtd.id " +
            "where ld.despesas_id = :id")
    List<Object> findAllByDespesas(Short id);


}
