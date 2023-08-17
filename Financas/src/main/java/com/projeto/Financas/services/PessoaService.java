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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private static PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    private static ArquivosUploadService arquivosUploadService;

    @Autowired
    private static UsuarioRepository usuarioRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, ArquivosUploadService arquivosUploadService) {
        PessoaService.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
        PessoaService.usuarioRepository = usuarioRepository;
        PessoaService.arquivosUploadService = arquivosUploadService;
    }


    @Transactional(rollbackFor = DomainException.class)
    public void adicionarPessoa(PessoaDTO pessoaDTO) throws DomainException {
        try {
            String senhaCripto = passwordEncoder.encode(pessoaDTO.getUsuario().getSenha());
            pessoaDTO.getUsuario().setSenha(senhaCripto);
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(pessoaDTO.getNome());
            pessoa.setSobrenome(pessoaDTO.getSobrenome());
            pessoa.setCpf(pessoaDTO.getCpf());
            pessoa.setEmail(pessoaDTO.getEmail());
            arquivosUploadService.save(pessoaDTO.getFile());
            Usuario usuario = new Usuario();
            usuario.setLogin(pessoaDTO.getCpf());
            usuario.setSenha(pessoaDTO.getUsuario().getSenha());
            usuario.setDocumento(arquivosUploadService.save(pessoaDTO.getFile()));
            usuarioRepository.save(usuario);
            pessoa.setUsuario(usuario);
            pessoaRepository.save(pessoa);
        } catch (DataAccessException e) {
            throw new DomainException("Não foi possivel criar o usuario.",e);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possivel criar o usuario",e);
        }
    }

    public Optional<Pessoa> findById(Short id) {
        return pessoaRepository.findById(id);
    }


    public ResponseEntity<String> deleteById(Short id) {
        pessoaRepository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }

    public Optional<Pessoa> findByPessoaByUser(String login) {
        Optional<Usuario> user = usuarioRepository.findByLogin(login);
        return pessoaRepository.findByUsuario(user.get());
    }
}
