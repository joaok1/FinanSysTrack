package com.projeto.Financas.controller;

import com.projeto.Financas.DTO.DespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.DashBoard.DadosDespesa;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.repository.DespesasRepository;
import com.projeto.Financas.services.DespesasService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@NoArgsConstructor
@RequestMapping("/api/despesas/")
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

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "pageLista/{id}", produces = "application/json")
    public Page<Despesas> inserirDespesas(@PathVariable String id , Pageable pageable) throws DomainException {
        if (Objects.isNull(id)) {
            throw new DomainException("id Usuario invalido");
        }
        return despesasService.getPageDespesas(id, pageable);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @DeleteMapping(value = "delete/{id}", produces = "application/json")
    public void deleteByDespesas(@PathVariable Short id) throws DomainException {
        if (Objects.isNull(id)) {
            throw new DomainException("id Despesa invalido");
        }
        despesasService.deleteByDespesas(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PutMapping("edititionFindById")
    public ResponseEntity<String> edititionFindById(@RequestBody Despesas despesas) throws DomainException {
        despesasService.edititionFindById(despesas);
        return ResponseEntity.ok("Registro alterado com sucesso");
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "visualizarDespesas/{id}")
    public Despesas findDespesasById(@PathVariable Short id) throws DomainException {
        return despesasService.findDespesasById(id);
    }


    // Serviço de LISTAGEM RELATORIO ou Gerar Relatorio PDF
    @CrossOrigin(origins ="http://localhost:8080/")
    @GetMapping("/relatorioDownload/{id}")
    public byte[] relatorio(@PathVariable Short id) throws Exception {
            return despesasService.getRelatorio(id);
        }

    // Serviço de LISTAGEM RELATORIO ou Gerar Relatorio PDF
    @CrossOrigin(origins ="http://localhost:8080/")
    @GetMapping("grafico/{user}/{ano}")
    public DadosDespesa[] grafico(@PathVariable String user, @PathVariable Integer ano) throws Exception {
        return despesasService.getDadosGrafico(user, ano);
    }

    @CrossOrigin(origins ="http://localhost:8080/")
    @GetMapping("grafico/{user}")
    public Object[] anos(@PathVariable String user) throws Exception {
        return despesasService.getAnos(user);
    }
    //Servico de listagem do Grafico de area
    @CrossOrigin(origins ="http://localhost:8080/")
    @GetMapping("graficoArea/{user}")
    public List<DadosDespesa> graficoArea(@PathVariable String user) throws Exception {
        return despesasService.getGraficoArea(user);
    }

}
