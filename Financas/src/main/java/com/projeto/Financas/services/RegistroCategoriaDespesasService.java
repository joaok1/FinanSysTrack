package com.projeto.Financas.services;

import com.projeto.Financas.DTO.RegistroCategoriaDespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.TipoDespesas;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.CategoriaDespesasRepository;
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
    private TipoDespesasRepository tipoDespesasRepository;
    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    public RegistroCategoriaDespesasService(CategoriaDespesasRepository categoriaDespesasRepository,
                                            UsuarioRepository usuarioRepository, TipoDespesasRepository tipoDespesasRepository) {
            RegistroCategoriaDespesasService.categoriaDespesasRepository = categoriaDespesasRepository;
            this.usuarioRepository = usuarioRepository;
            this.tipoDespesasRepository = tipoDespesasRepository;
    }

    public CategoriaDespesas inserirNovaCategoriaDespesas(RegistroCategoriaDespesasDTO categoriaDespesasDTO) throws DomainException {
        if (Objects.isNull(categoriaDespesasDTO.getTipo()) && Objects.isNull(categoriaDespesasDTO.getUsuario())) {
            throw new DomainException("Não foi possivel inserir os dados, á dados com valor nulo.");
        }
        List<CategoriaDespesas> categoriaDespesasList = categoriaDespesasRepository.findByName(categoriaDespesasDTO.getName());
        if (Objects.nonNull(categoriaDespesasList)) {
            throw new DomainException("Categoria já registrada!");
        }
            CategoriaDespesas categoriaDespesas = new CategoriaDespesas();

            Optional<TipoDespesas> registroTipoDespesas = tipoDespesasRepository.findById(categoriaDespesasDTO.getTipo());
            categoriaDespesas.setTipo(registroTipoDespesas.get());
            Optional<Usuario> usuario = usuarioRepository.findById(categoriaDespesasDTO.getUsuario());
            categoriaDespesas.setUsuario(usuario.get());
            categoriaDespesas.setData(new Date());
            categoriaDespesas.setName(categoriaDespesasDTO.getName());
            categoriaDespesasRepository.save(categoriaDespesas);
            //Criou um novo construtor para retornar somente 2 dados posso resgatar somente esses dados ou setalos e etc....
            CategoriaDespesas categoriaDespesas1 = new CategoriaDespesas(categoriaDespesas.getId(), categoriaDespesas.getName());
            return categoriaDespesas1;
    }

    public List<CategoriaDespesas> findAllByUsuario(Short id) throws DomainException {
        try {
            if (Objects.isNull(id)) {
                throw new DomainException("Id com valor nulo");
            }
            List<CategoriaDespesas> list = categoriaDespesasRepository.findAllByUsuario(id);
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

    public Page<CategoriaDespesas> findAllByUsuarioPage(Pageable pageable, Short id) throws DomainException {
        try {
            if (Objects.isNull(id)) {
                throw new DomainException("Id com valor nulo");
            }
            Page<CategoriaDespesas> list = categoriaDespesasRepository.findAllByUsuarioPage(pageable,id);
            return list;
        } catch (DomainException e) {
            throw e;
        }
    }

    public void deleteById(Short id) {
        categoriaDespesasRepository.deleteById(id);
    }
}
