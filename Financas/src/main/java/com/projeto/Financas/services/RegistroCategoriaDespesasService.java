package com.projeto.Financas.services;

import com.projeto.Financas.DTO.RegistroCategoriaDespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.ListagemDespesas;
import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.CategoriaDespesasRepository;
import com.projeto.Financas.repository.ListagemDespesasRepository;
import com.projeto.Financas.repository.TipoDespesasRepository;
import com.projeto.Financas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RegistroCategoriaDespesasService {

    private static CategoriaDespesasRepository categoriaDespesasRepository;
    @Autowired
    private ListagemDespesasRepository listagemDespesasRepository;
    @Autowired
    private TipoDespesasRepository tipoDespesasRepository;
    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    public RegistroCategoriaDespesasService(CategoriaDespesasRepository categoriaDespesasRepository,
        UsuarioRepository usuarioRepository, TipoDespesasRepository tipoDespesasRepository, ListagemDespesasRepository listagemDespesasRepository) {
            RegistroCategoriaDespesasService.categoriaDespesasRepository = categoriaDespesasRepository;
            this.usuarioRepository = usuarioRepository;
            this.tipoDespesasRepository = tipoDespesasRepository;
            this.listagemDespesasRepository = listagemDespesasRepository;
    }

    public CategoriaDespesas inserirNovaCategoriaDespesas(RegistroCategoriaDespesasDTO categoriaDespesasDTO) throws DomainException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(categoriaDespesasDTO.getUsuario());
        if (Objects.isNull(categoriaDespesasDTO.getTipo()) && Objects.isNull(categoriaDespesasDTO.getUsuario())) {
            throw new DomainException("Não foi possivel inserir os dados, á dados com valor nulo.");
        }
        List<CategoriaDespesas> categoriaDespesasList = categoriaDespesasRepository.findByNameAndUsuario(categoriaDespesasDTO.getName(),usuario.get());
        if (categoriaDespesasList.size() > 0) {
            throw new DomainException("Categoria já registrada!");
        }
            CategoriaDespesas categoriaDespesas = new CategoriaDespesas();

            Optional<TipoDespesas> registroTipoDespesas = tipoDespesasRepository.findById(categoriaDespesasDTO.getTipo());
            categoriaDespesas.setTipo(registroTipoDespesas.get());
            categoriaDespesas.setUsuario(usuario.get());
            categoriaDespesas.setData(new Date());
            categoriaDespesas.setName(categoriaDespesasDTO.getName());
            categoriaDespesas.setUsage(0);
            categoriaDespesasRepository.save(categoriaDespesas);
            //Criou um novo construtor para retornar somente 2 dados posso resgatar somente esses dados ou setalos e etc....
            CategoriaDespesas categoriaDespesas1 = new CategoriaDespesas(categoriaDespesas.getId(), categoriaDespesas.getName());
            return categoriaDespesas1;
    }

    public List<CategoriaDespesas> findAllByUsuario(String id) throws DomainException {
        try {
            if (Objects.isNull(id)) {
                throw new DomainException("Id com valor nulo");
            }
            Optional<Usuario> usuario = usuarioRepository.findByLogin(id);
            List<CategoriaDespesas> list = categoriaDespesasRepository.findAllByUsuario(usuario.get().getId());
            return list;
        } catch (DomainException e) {
            throw e;
        }
    }

    public void findByEdit( RegistroCategoriaDespesasDTO categoriaDespesasDTO) throws DomainException {
        try{
            if (Objects.isNull(categoriaDespesasDTO.getId()) && Objects.isNull(categoriaDespesasDTO.getTipo())) {
                throw  new DomainException("Id com valor nulo");
            } else if (Objects.isNull(categoriaDespesasDTO.getId()) || Objects.isNull(categoriaDespesasDTO.getTipo())) {
                throw  new DomainException("Id com valor nulo");
            }
            Optional<CategoriaDespesas> categoriaDespesas = categoriaDespesasRepository.findById(categoriaDespesasDTO.getId());
            List<ListagemDespesas> listagemDespesas = listagemDespesasRepository.findBydespesasCategory(categoriaDespesas.get());
            if (listagemDespesas.size() > 0) {
                throw new DomainException("Este registro não pode ser editado pois já esta sendo utilizado.");
            }
            Optional<CategoriaDespesas> registroCategoriaDespesas = categoriaDespesasRepository.findById(categoriaDespesasDTO.getId());
            CategoriaDespesas registro = registroCategoriaDespesas.get();
            registro.setName(categoriaDespesasDTO.getName());
            Optional<TipoDespesas> registroTipoDespesas = tipoDespesasRepository.findById(categoriaDespesasDTO.getTipo());
             registro.setTipo(registroTipoDespesas.get());
             categoriaDespesasRepository.save(registro);
        } catch (DomainException e) {
            e.printStackTrace();
             throw e;
        }
    }

    public Page<CategoriaDespesas> findAllByUsuarioPage(Pageable pageable, String id) throws DomainException {
        try {
            if (Objects.isNull(id)) {
                throw new DomainException("Id com valor nulo");
            }
            Optional<Usuario> usuario = usuarioRepository.findByLogin(id);
            Page<CategoriaDespesas> list = categoriaDespesasRepository.findAllByUsuarioPage(pageable,usuario.get().getId());
            return list;
        } catch (DomainException e) {
            throw e;
        }
    }

    public void deleteById(Short id) throws DomainException {
        Optional<CategoriaDespesas> categoriaDespesas = categoriaDespesasRepository.findById(id);
        List<ListagemDespesas> listagemDespesas = listagemDespesasRepository.findBydespesasCategory(categoriaDespesas.get());
        if (listagemDespesas.size() > 0) {
            throw new DomainException("Este registro ja esta sendo utilizado, não e possivel deletar.");
        } else {
            categoriaDespesasRepository.deleteById(id);
        }
    }
}
