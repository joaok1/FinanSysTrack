package com.projeto.Financas.controller;

import com.projeto.Financas.DTO.PessoaDTO;
import com.projeto.Financas.model.Pessoa;
import com.projeto.Financas.repository.PessoaRepository;
import com.projeto.Financas.services.PessoaService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("pessoa")
@Slf4j
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private static PessoaRepository pessoaRepository;

    public PessoaController(PessoaService pessoaService, PessoaRepository pessoaRepository) {
        this.pessoaService = pessoaService;
        this.pessoaRepository = pessoaRepository;
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping(value = "/adicionar", produces = "application/json")
    public ResponseEntity<String> salvarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        pessoaService.adicionarPessoa(pessoaDTO);
        return ResponseEntity.ok("Pessoa Cadastrada com sucesso");
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "/findById/{id}", produces = "application/json")
    public Optional<Pessoa> findById(@PathVariable Short id) {
        return pessoaService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @DeleteMapping(value = "/deleteById/{id}", produces = "application/json")
    public ResponseEntity<String> deleteById(@PathVariable Short id) {
        return pessoaService.deleteById(id);
    }
}
