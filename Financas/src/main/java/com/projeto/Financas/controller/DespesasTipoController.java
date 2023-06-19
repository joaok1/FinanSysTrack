package com.projeto.Financas.controller;


import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.repository.TipoDespesasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("despesasTipo")
public class DespesasTipoController {


    private TipoDespesasRepository tipoDespesasRepository;
    public DespesasTipoController(){};
    @Autowired
    public DespesasTipoController(TipoDespesasRepository tipoDespesasRepository) {
        this.tipoDespesasRepository = tipoDespesasRepository;
    }

    // Listagem Geral
    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/listar")
    public List<TipoDespesas> findAll() {
        List<TipoDespesas> registroDespesas = tipoDespesasRepository.findAll();
        return registroDespesas;
    }


}
