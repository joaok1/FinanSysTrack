package com.projeto.Financas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "despesas")
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @JsonIgnoreProperties(value = {"despesas", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "despesas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListagemDespesas> listagemDespesas;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="GMT-3")
    private Date calendar;

    private Double total;

    @Column(nullable = false)
    private Double entrada;

    private Double saldo;

    @Transient
    private Double valorAlimentacao;

    @Transient
    private Double valorRefeicao;

    @Transient
    private Double valorDiversos;

    @Transient
    private Double valorMobilidade;

    @Transient
    private Double valorFarmacia;

    @OneToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Usuario usuario;
}
