package com.projeto.Financas.services;

import com.projeto.Financas.DTO.PessoaDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.model.Pessoa;
import com.projeto.Financas.model.Usuario;
import com.projeto.Financas.repository.PessoaRepository;
import com.projeto.Financas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private static PessoaRepository pessoaRepository;

    @Autowired
    private static UsuarioRepository usuarioRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, UsuarioRepository usuarioRepository) {
        PessoaService.pessoaRepository = pessoaRepository;
        PessoaService.usuarioRepository = usuarioRepository;
    }

    @Transactional(rollbackFor = DomainException.class)
    public void adicionarPessoa(PessoaDTO pessoaDTO) throws DomainException {
        try {
            Pessoa pessoa = new Pessoa();

            pessoa.setNome(pessoaDTO.getNome());
            pessoa.setSobrenome(pessoaDTO.getSobrenome());
            pessoa.setData_nascimento(pessoaDTO.getData_nascimento());
            pessoa.setCpf(pessoaDTO.getCpf());
            pessoa.setRg(pessoaDTO.getRg());
            pessoa.setEndereco(pessoaDTO.getEndereco());
            pessoa.setCep(pessoaDTO.getCep());
            pessoa.setCidade(pessoaDTO.getCidade());
            pessoa.setEstado(pessoaDTO.getEstado());
            pessoa.setTelefone(pessoaDTO.getTelefone());
            pessoa.setEmail(pessoaDTO.getEmail());

            Usuario usuario = new Usuario();
            usuario.setLogin(pessoaDTO.getCpf());
            usuario.setSenha(pessoaDTO.getUsuario().getSenha());
            usuarioRepository.save(usuario);
            pessoa.setUsuario(usuario);
            pessoaRepository.save(pessoa);
        } catch (DataAccessException e) {
            throw new DomainException("Não foi possivel criar o usuario.",e);
        }
    }

    public Optional<Pessoa> findById(Short id) {
        return pessoaRepository.findById(id);
    }


    public ResponseEntity<String> deleteById(Short id) {
        pessoaRepository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }
}
