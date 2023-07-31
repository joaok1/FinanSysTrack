package com.projeto.Financas.services;

import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.DashBoard.DadosDespesa;
import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.TipoDespesasRepository;
import com.projeto.Financas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoDespesasService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoDespesasRepository tipoDespesasRepository;

    public void inserirTipo(TipoDespesas tipoDespesas) {
        TipoDespesas tipo = new TipoDespesas();
        tipo.setName(tipoDespesas.getName());
        tipoDespesasRepository.save(tipo);
    }

//    public List<DadosDespesa> gettipo(String user) throws DomainException {
//        Optional<Usuario> usuario = usuarioRepository.findByLogin(user);
//        if (Objects.isNull(usuario.get())) {
//            throw new DomainException("Usuario nao localizado na base de dados");
//        }
//        Object[] objects =  tipoDespesasRepository.findByAllDadosAnosUsuario(usuario.get());
//        List<Object> objectList = new ArrayList<>();
//        for (Object obj:objects) {
//            Object[] objArray = (Object[]) obj;
//            Double valor = (Double) objArray[0];
//            String name = (String) objArray[1];
//            Object[] object = new Object[2];
//            object[0] = valor;
//            object[1] = name;
//            objectList.add(object);
//        }
//        return objectList;
//    }
    public List<DadosDespesa> gettipo(String user, Integer ano) throws DomainException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(user);
        if (Objects.isNull(usuario.get())) {
            throw new DomainException("Usuario nao localizado na base de dados");
        }
        Object[] objects =  tipoDespesasRepository.findByAllDadosAnosUsuario(usuario.get(),ano);
        List<DadosDespesa> objectList = new ArrayList<>();
        for (Object obj:objects) {
            Object[] objArray = (Object[]) obj;
            Double valor = (Double) objArray[0];
            String name = (String) objArray[1];
            DadosDespesa object = new DadosDespesa();
            object.setName(name);
            object.setSaida(valor);
            objectList.add(object);
        }
        return objectList;
    }
}
