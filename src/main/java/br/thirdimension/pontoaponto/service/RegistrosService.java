/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.service;

import br.thirdimension.pontoaponto.model.RegistrosDia;
import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.RegistrosDiaRepository;
import br.thirdimension.pontoaponto.repository.RegistrosRepository;
import br.thirdimension.pontoaponto.repository.UsuarioRepository;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.LocalDate;
import java.time.LocalTime;
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
    
    public Registros inserirRegistroDiaManual(LocalTime hora) throws Exception{
        Registros registro = registrosRepository.buscarUltimoRegistroInserido(LocalDate.now(), usuarioSessao.getUsuario());
        registro.getRegistrosDia().add(new RegistrosDia(hora, registro));
        registrosRepository.save(registro);
        return registro;
    }
    
    public void removerRegistro(long id){
        Optional<Registros> registrosGerais = registrosRepository.findById(id);
        registrosRepository.delete(registrosGerais.get());
    }
    
    public void removerRegistroDia(long id){
        Optional<RegistrosDia> registrosGerais = registrosDiaRepository.findById(id);
        registrosDiaRepository.delete(registrosGerais.get());
    }
    
    public Page<Registros> buscarListaDeRegistrosDoUsuario(Pageable pageable){
        Page<Registros> listaDeRegistros = null;
        try{
           listaDeRegistros = registrosRepository.findByUsuario(usuarioSessao.getUsuario(), pageable);
        }catch(Exception ex){
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
    
    public Page<Registros> buscarListaDeRegistrosEntreDatasPorUsuario(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable){
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistroEntreDatasPorUsuario(dataInicial, dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
    
    public Page<Registros> buscarListaDeRegistrosApartirDaData(LocalDate dataInicial, Pageable pageable){
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosApartirDaData(dataInicial, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
    
    public Page<Registros> buscarListaDeRegistrosAteAData(LocalDate dataFinal, Pageable pageable){
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosAteAData(dataFinal, usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
        
    public Registros buscarUltimoRegistroInserido(Usuario usuario){
        Registros registros = null;
        try {
            registros = registrosRepository.buscarUltimoRegistroInserido(LocalDate.now(), usuario);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;      
    }
    
    public Page<Registros> buscarListaDeRegistrosIncompletos(Pageable pageable){
        Page<Registros> listaDeRegistros = null;
        try {
            listaDeRegistros = registrosRepository.listarRegistrosIncompletos(usuarioSessao.getUsuario(), pageable);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
        
    }
}
