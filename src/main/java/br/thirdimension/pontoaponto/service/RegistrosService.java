/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.service;

import br.thirdimension.pontoaponto.dto.TarefaDto;
import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.RegistrosDia;
import br.thirdimension.pontoaponto.model.Tarefa;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.RegistrosDiaRepository;
import br.thirdimension.pontoaponto.repository.RegistrosRepository;
import br.thirdimension.pontoaponto.repository.TarefaRepository;
import br.thirdimension.pontoaponto.repository.UsuarioRepository;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author clovis.rodrigues
 */
@Service
public class RegistrosService {

    @Autowired
    private UsuarioSessao usuarioSessao;
    
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private RegistrosRepository registrosRepository;

    @Autowired
    private RegistrosDiaRepository registrosDiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Registros inserirRegistroManual(LocalDate data) throws Exception{
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioSessao.getUsuario().getId());
        Registros registrosGerais = new Registros(data, usuarioSessao.getUsuario());
        usuario.get().getRegistros().add(registrosGerais);
        registrosRepository.save(registrosGerais);
        return registrosGerais;
    }

    public Registros inserirRegistroDiaManual(Long id, LocalTime hora) throws Exception {
        Optional<Registros> registro = registrosRepository.findById(id);
        registro.get().getRegistrosDia().add(new RegistrosDia(hora, registro.get()));
        registrosRepository.save(registro.get());
        return registro.get();
    }

    public void removerRegistro(long id) {
        Optional<Registros> registrosGerais = registrosRepository.findById(id);
        registrosRepository.delete(registrosGerais.get());
    }

    public void removerRegistroDia(long id) {
        Optional<RegistrosDia> registrosGerais = registrosDiaRepository.findById(id);
        registrosDiaRepository.delete(registrosGerais.get());
    }

    public Page<Registros> buscarListaDeRegistros(Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.findByUsuario(usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Page<Registros> buscarListaDeRegistrosEntreDatasPorUsuario(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistroEntreDatasPorUsuario(dataInicial, dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Page<Registros> buscarListaDeRegistrosApartirDaData(LocalDate dataInicial, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosApartirDaData(dataInicial, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Page<Registros> buscarListaDeRegistrosAteAData(LocalDate dataFinal, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosAteAData(dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Registros buscarRegistroPorData(Usuario usuario, LocalDate localDate) {
        Registros registros = null;
        try {
            registros = registrosRepository.buscarRegistroPorData(localDate, usuario);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;
    }

    public Page<Registros> buscarListaDeRegistrosIncompletos(Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosIncompletos(usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;

    }

    public Page<Registros> buscarListaDeRegistrosIncompletosEntreDatas(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosIncompletosEntreDatasPorUsuario(dataInicial, dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Page<Registros> buscarListaRegistrosIncompletosApartirDaData(LocalDate dataInicial, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosIncompletosApartirDaData(dataInicial, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Page<Registros> buscarListaRegistrosIncompletosAteAData(LocalDate dataFinal, Pageable pageable) {
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosIncompletosAteAData(dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }

    public Registros buscarRegistroPorId(long id) {
        Optional<Registros> registro = registrosRepository.findById(id);
        return registro.get();
    }
    
    public List<Registros> buscarListaRegistrosCompletos() {
        List<Registros> listaDeRegistros = new ArrayList<>();
        try {
            listaDeRegistros = registrosRepository.listarRegistrosCompletosPorUsuario(usuarioSessao.getUsuario());
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
    
    public void removerTarefa(long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        tarefaRepository.delete(tarefa.get());
    }
    
    public Tarefa inserirTarefa(TarefaDto tarefaDto) throws Exception {
        Optional<Registros> registro = registrosRepository.findById(tarefaDto.getRegistroId());
        Tarefa tarefa = new Tarefa(tarefaDto.getDescricao(), tarefaDto.getTempo(), registro.get());
        registro.get().getTarefa().add(tarefa);
        tarefaRepository.save(tarefa);
        return tarefa;
    }
}
