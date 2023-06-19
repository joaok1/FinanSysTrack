package com.projeto.Financas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro_categoria_despesas")
public class CategoriaDespesas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Short id;
    private String name;
    @OneToOne
    @JoinColumn(name = "tipo",referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TipoDespesas tipo;
    @OneToOne
    @JoinColumn(name = "usuario",referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Usuario usuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="GMT-3")
    @Column(name = "data_cadastro")
    private Date data;

    public CategoriaDespesas(Short id, String name) {
        this.id = id;
        this.name = name;
    }
}
