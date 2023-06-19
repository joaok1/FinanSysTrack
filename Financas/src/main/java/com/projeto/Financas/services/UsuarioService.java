package com.projeto.Financas.services;

import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private static UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        UsuarioService.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findById(Short id) {
        return usuarioRepository.findById(id);
    }
}
