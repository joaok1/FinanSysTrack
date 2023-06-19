package com.projeto.Financas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "despesas")
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Transient
    @OneToMany
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<ListagemDespesas> listagemDespesas;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="GMT-3")
    Calendar calendar = Calendar.getInstance();
    private int ano = calendar.get(Calendar.YEAR);
    @Column(nullable = false)
    private String mes;
    private Double total;
    @Column(nullable = false)
    private Double entrada;
    private Double saldo;
    @OneToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Usuario usuario;
}
