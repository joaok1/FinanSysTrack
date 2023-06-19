package com.projeto.Financas.controller;


import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.UsuarioRepository;
import com.projeto.Financas.services.UsuarioService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/findById/{id}")
    public Optional<Usuario> findById(@PathVariable Short id) {
        return usuarioService.findById(id);
    }

}
