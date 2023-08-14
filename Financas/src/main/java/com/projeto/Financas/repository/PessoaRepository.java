package com.projeto.Financas.repository;


import com.projeto.Financas.model.Pessoa;
import com.projeto.Financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Short> {
    @Query(nativeQuery = true, value = "select * from pessoa p WHERE p.usuario_id = :usuario")
    Optional<Pessoa> findByIdUsuario(Short usuario);

    Optional<Pessoa> findByUsuario(Usuario usuario);
}
