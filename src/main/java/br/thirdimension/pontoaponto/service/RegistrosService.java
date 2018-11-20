/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.service;

import br.thirdimension.pontoaponto.api.client.REPApiClient;
import br.thirdimension.pontoaponto.controller.RegistrosController;
import br.thirdimension.pontoaponto.dto.Registros;
import br.thirdimension.pontoaponto.exception.REPException;
import br.thirdimension.pontoaponto.model.RegistrosGerais;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.RegistrosRepository;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private REPApiClient repApiClient;
    
    public List<Registros> buscarListaDeRegistrosREP() {
        List<Registros> registros = new ArrayList<>();
        try {
            registros = repApiClient.registros();
        } catch (REPException ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;
    }
    
    public void importarRegistros(List<Registros> registrosREP) {
        List<RegistrosGerais> registrosGerais = registrosRepository.findAll();
        List<Integer> nsr = new ArrayList<>();
        registrosGerais.forEach((gerais) -> {
            nsr.add(gerais.getNSR());
        });
        registrosREP.stream().filter((registros) -> (!nsr.contains(registros.getNsr()))).forEachOrdered((registros) -> {
            registrosRepository.save(new RegistrosGerais(registros.getDataHora(), registros.getPis(), registros.getNsr()));
        });
    }
    
    public RegistrosGerais inserirRegistroManual(Date data) throws Exception{
        Usuario usuario = usuarioSessao.getUsuario();
        RegistrosGerais registrosGerais = new RegistrosGerais(data, usuario.getPis(), 0);
        registrosRepository.save(registrosGerais);
        return registrosGerais;
    }
    
    public List<RegistrosGerais> buscarListaDeRegistrosDoUsuario(){
        List<RegistrosGerais> listaDeRegistros = new ArrayList<>();
        try{
           listaDeRegistros = registrosRepository.findByPis(usuarioSessao.getUsuario().getPis());
        }catch(Exception ex){
            Logger.getLogger(RegistrosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeRegistros;
    }
    
}
