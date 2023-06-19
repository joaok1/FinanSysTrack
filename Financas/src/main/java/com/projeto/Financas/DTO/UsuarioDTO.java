package com.projeto.Financas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UsuarioDTO {
    private Short id;
    private String login;
    private String senha;
}
