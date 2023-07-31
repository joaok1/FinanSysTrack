package com.projeto.Financas.repository;

import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespesasRepository extends JpaRepository<TipoDespesas,Short> {
    @Query(nativeQuery = true, value = "select SUM(ld.valor) AS valor, rtd.name  from listagem_despesas ld " +
            "inner join registro_categoria_despesas rcd " +
            "on ld.registro_categoria_despesas_id  = rcd.id " +
            "inner join registro_tipo_despesas rtd " +
            "on rcd.tipo = rtd.id " +
            "where rcd.usuario = :usuario " +
            "GROUP BY rtd.name ")
    Object[] findByAllDadosAnosUsuario(Usuario usuario);
}
