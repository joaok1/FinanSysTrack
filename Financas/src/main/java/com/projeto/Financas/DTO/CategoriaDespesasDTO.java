package com.projeto.Financas.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDespesasDTO {

    private Short id;
    private String name;
    private TipoDespesas tipo;
    private Short usuario;
    private Date data;
}
