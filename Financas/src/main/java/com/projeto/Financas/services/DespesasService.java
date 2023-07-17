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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            despesas.setCalendar(despesasDTO.getCalendar());
            despesas.setUsuario(usuario.get());
            despesas.setEntrada(despesasDTO.getEntrada());
            Double soma  = 0.0;
            for(ListagemDespesas list : despesasDTO.getListagemDespesas()) {
                double valor = list.getValor();
                soma+=valor;
            }
            despesas.setTotal(soma);
            despesas.setSaldo(despesas.getEntrada() - despesas.getTotal());

            List<ListagemDespesas> listagemDespesasList = new ArrayList<>();
            for (ListagemDespesas list :despesasDTO.getListagemDespesas()) {
                ListagemDespesas listagemDespesas = new ListagemDespesas();
                Optional<CategoriaDespesas> categoriaDespesasOptional = categoriaDespesasRepository.findById(list.getDespesasCategory().getId());
                    if (!categoriaDespesasOptional.isPresent()){
                        throw new DomainException("Categoria de despesas invalido");
                    }
                    listagemDespesas.setDespesasCategory(categoriaDespesasOptional.get());
                    listagemDespesas.setUsuario(usuario.get());
                    listagemDespesas.setValor(list.getValor());
                    listagemDespesas.setDespesas(despesas);
                    listagemDespesasList.add(listagemDespesas);
            }
            despesas.setListagemDespesas(listagemDespesasList);
            despesasRepository.save(despesas);
        } catch (DomainException e){
            throw e;
        }

    }

    public Page<Despesas> getPageDespesas(Short id, Pageable pageable) throws DomainException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (Objects.isNull(usuario.get())) {
            throw new DomainException("Usuario não encontrado na base de dados!");
        }
        return despesasRepository.findByUsuario(id, pageable);
    }
    @Transactional
    public void deleteByDespesas(Short id) throws DomainException {
        Optional<Despesas> despesas = despesasRepository.findById(id);
        if (Objects.isNull(despesas)) {
           throw new DomainException("Despesa nao encontrada");
        }
        deleteByCascade(despesas.get());
        despesasRepository.deleteById(id);
    }
    @Transactional
    private void deleteByCascade(Despesas despesas) {
        listagemDespesasRepository.deleteByDespesas(despesas);
    }
}
//dentro do for poderia ir adicionando um lista e fora do laço salvar a lista de uma vez