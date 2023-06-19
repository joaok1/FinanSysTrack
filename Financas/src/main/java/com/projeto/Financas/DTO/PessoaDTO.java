package com.projeto.Financas.DTO;

import com.projeto.Financas.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PessoaDTO {
    private String nome;

    private String sobrenome;

    private Date data_nascimento;

    private String cpf;

    private String rg;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    private String telefone;

    private String email;

    private Usuario usuario;

}
