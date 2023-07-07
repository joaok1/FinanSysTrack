package com.projeto.Financas.controller;


import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.repository.TipoDespesasRepository;
import com.projeto.Financas.services.TipoDespesasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despesas/despesasTipo")
public class DespesasTipoController {


    private TipoDespesasRepository tipoDespesasRepository;

    @Autowired
    private TipoDespesasService tipoDespesasService;
    public DespesasTipoController(){};
    @Autowired
    public DespesasTipoController(TipoDespesasRepository tipoDespesasRepository,TipoDespesasService tipoDespesasService) {
        this.tipoDespesasRepository = tipoDespesasRepository;
        this.tipoDespesasService = tipoDespesasService;
    }

    // Listagem Geral
    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/listar")
    public List<TipoDespesas> findAll() {
        List<TipoDespesas> registroDespesas = tipoDespesasRepository.findAll();
        return registroDespesas;
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping("/inserir")
    public void inserir(@RequestBody TipoDespesas tipoDespesas) {
        tipoDespesasService.inserirTipo(tipoDespesas);
    }


}
