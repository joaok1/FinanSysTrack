package com.projeto.Financas.services;

import com.projeto.Financas.DTO.DespesasDTO;
import com.projeto.Financas.Exception.DomainException;
import com.projeto.Financas.Interfaces.Meses;
import com.projeto.Financas.model.CategoriaDespesas;
import com.projeto.Financas.model.DashBoard.DadosDespesa;
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

import java.util.*;

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
        Optional<Usuario> usuario = usuarioRepository.findByLogin(despesasDTO.getUsuario());
        try{
            if (Objects.isNull(despesasDTO.getUsuario())) {
                throw new DomainException("usuario invalido");
            }
            Despesas despesas = new Despesas();
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
                // Valor 0 quer dizer que nao esta sendo utilizado
                if (categoriaDespesasOptional.get().getUsage() == 0) {
                    categoriaDespesasOptional.get().setUsage(1);
                    categoriaDespesasRepository.save(categoriaDespesasOptional.get());
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

    public Page<Despesas> getPageDespesas(String id, Pageable pageable) throws DomainException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(id);
        if (Objects.isNull(usuario.get())) {
            throw new DomainException("Usuario não encontrado na base de dados!");
        }

        Page<Despesas> despesasPage = despesasRepository.findAllByUsuario(pageable, usuario.get());

        return despesasPage;
    }

    @Transactional
    public void deleteByDespesas(Short id) throws DomainException {
        Optional<Despesas> despesas = despesasRepository.findById(id);
        if (Objects.isNull(despesas)) {
           throw new DomainException("Despesa nao encontrada");
        }
       List<ListagemDespesas> list = deleteByCascade(id);
        if (list.size() == 0) {
            for (ListagemDespesas listagemDespesas : despesas.get().getListagemDespesas()) {
                CategoriaDespesas categoriaDespesas = listagemDespesas.getDespesasCategory();
                categoriaDespesas.setUsage(0);
                categoriaDespesasRepository.save(categoriaDespesas);
            }
        }
        despesasRepository.deleteById(id);
    }


    private List<ListagemDespesas> deleteByCascade(Short id) {
        return listagemDespesasRepository.get(id);

    }

    public void edititionFindById(Despesas despesas) throws DomainException {
        if (Objects.isNull(despesas)) {
            throw new DomainException("Despesas sem valor");
        }
        Optional<Despesas> despesasOptional = despesasRepository.findById(despesas.getId());
        Despesas despesasEdit = despesasOptional.get();
        despesasEdit.setEntrada(despesas.getEntrada());
        Double soma  = 0.0;
        for(ListagemDespesas list : despesas.getListagemDespesas()) {
            double valor = list.getValor();
            soma+=valor;
        }
        despesasEdit.setTotal(soma);
        despesasEdit.setSaldo(despesas.getEntrada() - despesasEdit.getTotal());
        List<ListagemDespesas> listagemDespesasList = despesasEdit.getListagemDespesas();
        listagemDespesasList.clear();
        for (ListagemDespesas listagemDespesas:despesas.getListagemDespesas()) {
            ListagemDespesas listagem = new ListagemDespesas();
            listagem.setDespesasCategory(listagemDespesas.getDespesasCategory());
            listagem.setValor(listagemDespesas.getValor());
            listagem.setUsuario(despesasEdit.getUsuario());
            listagem.setDespesas(despesas);
            listagemDespesasList.add(listagem);
        }
        despesasEdit.setListagemDespesas(listagemDespesasList);
        despesasRepository.save(despesasEdit);
    }

    public Despesas findDespesasById(Short id) throws DomainException {
        if (Objects.isNull(id)) {
            throw new DomainException("Id invalido");
        }
        List<Object> groupDados = listagemDespesasRepository.findAllTipoDespesas(id);
        Optional<Despesas> despesas = despesasRepository.findById(id);
        for (Object object:groupDados) {
            Object[] objArray = (Object[]) object;
            Double valor = (Double) objArray[0];
            String tipoDespesa = (String) objArray[1];
            switch (tipoDespesa){
                case "Alimentação":
                    despesas.get().setValorAlimentacao(valor);
                    break;
                case "Refeição":
                    despesas.get().setValorRefeicao(valor);
                    break;
                case "Mobilidade":
                    despesas.get().setValorMobilidade(valor);
                    break;
                case "Farmácia":
                    despesas.get().setValorFarmacia(valor);
                    break;
                case "Diversos":
                    despesas.get().setValorDiversos(valor);
                    break;
            }
        }
        return despesas.get();
    }

    public byte[] getRelatorio(Short id) throws Exception {
            Optional<Despesas> despesas = despesasRepository.findById(id);
            Despesas despesasObj = despesas.orElseThrow(() -> new NoSuchElementException("Despesas não encontradas"));
            List<Object> data = listagemDespesasRepository.findAllByDespesas(despesasObj.getId());
        return PdfGenerator.pdf(data);
            
    }

    public DadosDespesa[] getDadosGrafico(String user, Integer anos) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(user);
        List<Object[]> lista = despesasRepository.findByDadosDashBoardAnosUsuario(usuario.get(),anos);
        DadosDespesa[] despesasMes = new DadosDespesa[12];
        for (Object[] obj:lista) {
            Object[] objArray = (Object[]) obj;
            Integer ano = (Integer) objArray[0];
            String mes = (String) objArray[1];
            Double entrada = (Double) objArray[2];
            Double saida = (Double) objArray[3];
            DadosDespesa dadosDespesa = new DadosDespesa();
            dadosDespesa.setAno(ano);
            dadosDespesa.setEntrada(entrada);
            dadosDespesa.setSaida(saida);
            switch (mes) {
                case Meses.JANEIRO:
                    dadosDespesa.setMes("JANEIRO");
                    despesasMes[0] = dadosDespesa;
                    break;
                case Meses.FEVEREIRO:
                    dadosDespesa.setMes("FEVEREIRO");
                    despesasMes[1] = dadosDespesa;
                    break;
                case Meses.MARCO:
                    dadosDespesa.setMes("MARCO");
                    despesasMes[2] = dadosDespesa;
                    break;
                case Meses.ABRIL:
                    dadosDespesa.setMes("ABRIL");
                    despesasMes[3] = dadosDespesa;
                    break;
                case Meses.MAIO:
                    dadosDespesa.setMes("MAIO");
                    despesasMes[4] = dadosDespesa;
                    break;
                case Meses.JUNHO:
                    dadosDespesa.setMes("JUNHO");
                    despesasMes[5] = dadosDespesa;
                    break;
                case Meses.JULHO:
                    dadosDespesa.setMes("JULHO");
                    despesasMes[6] = dadosDespesa;
                    break;
                case Meses.AGOSTO:
                    dadosDespesa.setMes("AGOSTO");
                    despesasMes[7] = dadosDespesa;
                    break;
                case Meses.SETEMBRO:
                    dadosDespesa.setMes("SETEMBRO");
                    despesasMes[8] = dadosDespesa;
                    break;
                case Meses.OUTUBRO:
                    dadosDespesa.setMes("OUTUBRO");
                    despesasMes[9] = dadosDespesa;
                    break;
                case Meses.NOVEMBRO:
                    dadosDespesa.setMes("NOVEMBRO");
                    despesasMes[10] = dadosDespesa;
                    break;
                case Meses.DEZEMBRO:
                    dadosDespesa.setMes("DEZEMBRO");
                    despesasMes[11] = dadosDespesa;
                    break;
            }
        }
        return despesasMes;
    }


    public Object[] getAnos(String user) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(user);
        return despesasRepository.findByDadosDashBoardAnosUsuario(usuario.get());
    }

    public List<DadosDespesa> getGraficoArea(String user) throws DomainException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(user);
        if (Objects.isNull(usuario)) {
            throw new DomainException("Usuario não encontrado na base de dados");
        }
        List<Object> dadosDespesaList = despesasRepository.findByDadosDashBoardArea(usuario.get());
        List<DadosDespesa> despesaList = new ArrayList<>();
        for (Object obj:dadosDespesaList) {
            Object[] objArray = (Object[]) obj;
            Integer ano = (Integer) objArray[0];
            Double entrada = (Double) objArray[1];
            Double saida = (Double) objArray[2];
            DadosDespesa dadosDespesa = new DadosDespesa();
            dadosDespesa.setAno(ano);
            dadosDespesa.setEntrada(entrada);
            dadosDespesa.setSaida(saida);
            despesaList.add(dadosDespesa);
        }
        return despesaList;
    }
}

