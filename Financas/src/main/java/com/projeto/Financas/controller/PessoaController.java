package com.projeto.Financas.controller;

import com.projeto.Financas.DTO.PessoaDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.Pessoa;
import com.projeto.Financas.repository.PessoaRepository;
import com.projeto.Financas.services.PessoaService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pessoa/")
@Slf4j
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private static PessoaRepository pessoaRepository;


    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping(value = "adicionar", produces = "application/json")
    public ResponseEntity<String> salvarPessoa(@RequestBody PessoaDTO pessoaDTO) throws DomainException {
        String senhaCripto = passwordEncoder.encode(pessoaDTO.getUsuario().getSenha());
        pessoaDTO.getUsuario().setSenha(senhaCripto);
        pessoaService.adicionarPessoa(pessoaDTO);
        return ResponseEntity.ok("Pessoa Cadastrada com sucesso");
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "findById/{id}", produces = "application/json")
    public Optional<Pessoa> findById(@PathVariable Short id) {
        return pessoaService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @DeleteMapping(value = "deleteById/{id}", produces = "application/json")
    public ResponseEntity<String> deleteById(@PathVariable Short id) {
        return pessoaService.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "findByUser/{login}", produces = "application/json")
    public Optional<Pessoa> findByPessoaByUser(@PathVariable String login) {
        return pessoaService.findByPessoaByUser(login);
    }
}


