package com.projeto.Financas.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.Financas.model.ListagemDespesas;
import com.projeto.Financas.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesasDTO {

    private Short id;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<ListagemDespesas> listagemDespesas;
    Calendar calendar = Calendar.getInstance();
    private int ano = calendar.get(Calendar.YEAR);
    private String mes;
    private Double total;
    private Double entrada;
    private Double saldo;
    private Short usuario;
}
