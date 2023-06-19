package com.projeto.Financas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;


@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String nome;

    private String email;

    private String senha;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

}
