package com.projeto.Financas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroCategoriaDespesasDTO {

    private Short id;
    private String name;
    private Short tipo;
    private Short usuario;
    private Date data;
}
