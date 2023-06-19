package com.projeto.Financas.services;

import com.projeto.Financas.DTO.DespesasDTO;
import com.projeto.Financas.DTO.ListagemDespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.Despesas;
import com.projeto.Financas.model.ListagemDespesas;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.CategoriaDespesasRepository;
import com.projeto.Financas.repository.DespesasRepository;
import com.projeto.Financas.repository.ListagemDespesasRepository;
import com.projeto.Financas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DespesasService {

    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private  DespesasRepository despesasRepository;

    @Autowired
    private ListagemDespesasRepository listagemDespesasRepository;

    @Autowired
    private CategoriaDespesasRepository categoriaDespesasRepository;

    public DespesasService(UsuarioRepository usuarioRepository,DespesasRepository despesasRepository, CategoriaDespesasRepository categoriaDespesasRepository,  ListagemDespesasRepository listagemDespesasRepository) {
        this.usuarioRepository = usuarioRepository;
        this.despesasRepository = despesasRepository;
        this.categoriaDespesasRepository = categoriaDespesasRepository;
        this.listagemDespesasRepository = listagemDespesasRepository;

    }
    @Transactional(rollbackFor = DomainException.class)
    public void addDespesas(DespesasDTO despesasDTO) throws DomainException {
        try{
            if (Objects.isNull(despesasDTO.getUsuario())) {
                throw new DomainException("ID do usuario invalido");
            }
            Despesas despesas = new Despesas();
            Optional<Usuario> usuario = usuarioRepository.findById(despesasDTO.getUsuario());
            despesas.setUsuario(usuario.get());
            despesas.setMes(despesasDTO.getMes());
            despesas.setEntrada(despesasDTO.getEntrada());
            Double soma  = 0.0;
            for(ListagemDespesas list : despesasDTO.getListagemDespesas()) {
                double valor = list.getValor();
                soma+=valor;
            }
            despesas.setTotal(soma);
            despesas.setSaldo(despesas.getEntrada() - despesas.getTotal());
            despesasRepository.save(despesas);
            for (ListagemDespesas list :despesasDTO.getListagemDespesas()) {
                ListagemDespesas listagemDespesas = new ListagemDespesas();
                Optional<CategoriaDespesas> categoriaDespesasOptional = categoriaDespesasRepository.findById(list.getDespesasCategory().getId());
                    if (categoriaDespesasOptional.isEmpty()){
                        throw new DomainException("Categoria de despesas invalido");
                    }
                    listagemDespesas.setDespesasCategory(categoriaDespesasOptional.get());
                    listagemDespesas.setUsuario(usuario.get());
                    listagemDespesas.setValor(list.getValor());
                    listagemDespesas.setDespesas(despesas);
                    listagemDespesasRepository.save(listagemDespesas);

            }
        } catch (DomainException e){
            throw e;
        }

    }
}
