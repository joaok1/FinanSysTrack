package com.projeto.Financas.services;

import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.repository.TipoDespesasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoDespesasService {

    @Autowired
    private TipoDespesasRepository tipoDespesasRepository;

    public void inserirTipo(TipoDespesas tipoDespesas) {
        TipoDespesas tipo = new TipoDespesas();
        tipo.setName(tipoDespesas.getName());
        tipoDespesasRepository.save(tipo);
    }
}
