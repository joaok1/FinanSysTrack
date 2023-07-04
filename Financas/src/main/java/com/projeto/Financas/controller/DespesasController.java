package com.projeto.Financas.controller;

import com.projeto.Financas.DTO.DespesasDTO;
import com.projeto.Financas.DTO.PessoaDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.Pessoa;
import com.projeto.Financas.repository.DespesasRepository;
import com.projeto.Financas.repository.PessoaRepository;
import com.projeto.Financas.services.DespesasService;
import com.projeto.Financas.services.PessoaService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/api/despesas")
@Slf4j
public class DespesasController {
    @Autowired
    private DespesasService despesasService;
    @Autowired
    private static DespesasRepository despesasRepository;

    public DespesasController(DespesasService despesasService, DespesasRepository despesasRepository) {
        this.despesasService = despesasService;
        this.despesasRepository = despesasRepository;
    }
    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping(value = "adicionar", produces = "application/json")
    public void inserirDespesas(@RequestBody DespesasDTO despesas) throws DomainException {
        try {
            despesasService.addDespesas(despesas);
        } catch (DomainException e) {
            throw e;
        }
    }
}
