package com.projeto.Financas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listagem_despesas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListagemDespesas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @OneToOne
    @JoinColumn(name = "registro_categoria_despesas_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CategoriaDespesas despesasCategory;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "despesas", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Despesas despesas;
    private Double valor;
}
