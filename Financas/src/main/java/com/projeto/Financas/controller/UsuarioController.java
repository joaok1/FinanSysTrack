package com.projeto.Financas.controller;


import com.projeto.Financas.DTO.CredenciaisDTO;
import com.projeto.Financas.DTO.TokenDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.UsuarioRepository;
import com.projeto.Financas.securityJwt.JwtService;
import com.projeto.Financas.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/findById/{id}")
    public Optional<Usuario> findById(@PathVariable Short id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | DomainException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}
