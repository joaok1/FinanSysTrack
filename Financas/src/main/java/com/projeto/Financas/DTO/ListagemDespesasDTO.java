package com.projeto.Financas.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Usuario;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListagemDespesasDTO {

    private Short id;
    private CategoriaDespesasDTO despesasCategory;
    private Short usuario;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DespesasDTO despesas;
    private Double valor;
}
