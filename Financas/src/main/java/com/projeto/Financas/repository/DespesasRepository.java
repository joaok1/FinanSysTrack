package com.projeto.Financas.repository;

import com.projeto.Financas.model.DashBoard.DadosDespesa;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Array;
import java.util.List;

public interface DespesasRepository  extends JpaRepository<Despesas, Short> {

    Page<Despesas> findAllByUsuario(Pageable pageable, Usuario usuario);

    @Query(nativeQuery = true, value = "SELECT EXTRACT(YEAR FROM calendar) AS ano, " +
            "ROUND(SUM(entrada),2) AS entrada, " +
            "ROUND(SUM(total),2) AS saida " +
            "FROM despesas d " +
            "WHERE d.usuario = :usuario " +
            "GROUP BY EXTRACT(YEAR FROM calendar) " +
            "ORDER BY ano asc;")
    List<Object> findByDadosDashBoardArea(Usuario usuario);

    @Query(nativeQuery = true, value = "SELECT " +
            "EXTRACT(YEAR FROM calendar) AS ano, " +
            "DATE_FORMAT(calendar, '%M') AS mes, " +
            "ROUND(SUM(entrada),2) AS entrada, " +
            "ROUND(SUM(total),2) AS saida " +
            "FROM despesas d " +
            "WHERE d.usuario = :usuario and EXTRACT(YEAR FROM calendar) = :ano " +
            "GROUP BY EXTRACT(YEAR FROM calendar), DATE_FORMAT(calendar, '%M')")
    List<Object[]> findByDadosDashBoardAnosUsuario(Usuario usuario, Integer ano);

    @Query(nativeQuery = true, value = "SELECT EXTRACT(YEAR FROM calendar) AS ano " +
            "FROM despesas d " +
            "WHERE d.usuario = :usuario " +
            "GROUP BY EXTRACT(YEAR FROM calendar) " +
            "ORDER BY ano asc")
    Object[] findByDadosDashBoardAnosUsuario(Usuario usuario);

}
