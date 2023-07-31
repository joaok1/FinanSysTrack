package com.projeto.Financas.repository;

import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespesasRepository extends JpaRepository<TipoDespesas,Short> {
    @Query(nativeQuery = true, value = "select SUM(ld.valor) AS valor, rtd.name, EXTRACT(YEAR FROM d.calendar) AS ano from listagem_despesas ld " +
            "inner join despesas d " +
            "on ld.despesas_id = d.id " +
            "inner join registro_categoria_despesas rcd " +
            "on ld.registro_categoria_despesas_id  = rcd.id " +
            "inner join registro_tipo_despesas rtd " +
            "on rcd.tipo = rtd.id " +
            "where rcd.usuario = :usuario and EXTRACT(YEAR FROM d.calendar)=:ano " +
            "GROUP BY rtd.name,  EXTRACT(YEAR FROM d.calendar)")
    Object[] findByAllDadosAnosUsuario(Usuario usuario, Integer ano);
}
