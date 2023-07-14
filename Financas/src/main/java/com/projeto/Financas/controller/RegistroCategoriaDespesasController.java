package com.projeto.Financas.controller;

import com.projeto.Financas.DTO.RegistroCategoriaDespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.repository.CategoriaDespesasRepository;
import com.projeto.Financas.services.RegistroCategoriaDespesasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/registroCategoriaDespesas")
@CrossOrigin(origins = "http://localhost:8080/")
public class RegistroCategoriaDespesasController {
    @Autowired
    private RegistroCategoriaDespesasService registroCategoriaDespesasService;
    @Autowired
    private static  CategoriaDespesasRepository categoriaDespesasRepository;

    public RegistroCategoriaDespesasController(RegistroCategoriaDespesasService registroCategoriaDespesasService, CategoriaDespesasRepository categoriaDespesasRepository) {
        this.registroCategoriaDespesasService = registroCategoriaDespesasService;
        RegistroCategoriaDespesasController.categoriaDespesasRepository = categoriaDespesasRepository;
    }


    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping("/inserir")
    public ResponseEntity<CategoriaDespesas> inserir(@RequestBody RegistroCategoriaDespesasDTO categoriaDespesasDTO) throws DomainException {
        CategoriaDespesas regi = registroCategoriaDespesasService.inserirNovaCategoriaDespesas(categoriaDespesasDTO);
        return ResponseEntity.ok().body(regi);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/listar/{id}")
    public List<CategoriaDespesas> getList(@PathVariable Short id) throws DomainException {
        try {
            List<CategoriaDespesas> list = registroCategoriaDespesasService.findAllByUsuario(id);
            return list;
        } catch (DomainException e) {
            throw e;
        }
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/listar/page/{id}")
    public Page<CategoriaDespesas> getListPage( Pageable pageable,@PathVariable Short id) throws DomainException {
        try {
            Page<CategoriaDespesas> list = registroCategoriaDespesasService.findAllByUsuarioPage(pageable,id);
            return list;
        } catch (DomainException e) {
            throw e;
        }
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping("/listarFindById/{id}")
    public Optional<CategoriaDespesas> getListById(@PathVariable Short id) throws DomainException {
        if (Objects.isNull(id)) {
            throw new DomainException("Id com valor nulo");
        }
            Optional<CategoriaDespesas> categoriaDespesas = categoriaDespesasRepository.findById(id);
            return categoriaDespesas;
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Short id) throws DomainException {
        if (Objects.isNull(id)) {
            throw new DomainException("Id com valor nulo");
        }
        registroCategoriaDespesasService.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PutMapping("/edit")
    public void editar(@RequestBody RegistroCategoriaDespesasDTO categoriaDespesasDTO) throws DomainException {
        try {
            registroCategoriaDespesasService.findByEdit(categoriaDespesasDTO);
        } catch (DomainException e) {
             throw e;
        }
    }

}
