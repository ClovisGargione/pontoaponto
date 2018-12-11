/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.DiaDeTrabalho;
import br.thirdimension.pontoaponto.dto.GraficoParametros;
import br.thirdimension.pontoaponto.dto.RegistrosDto;
import br.thirdimension.pontoaponto.negocio.RegistrosNegocio;
import br.thirdimension.pontoaponto.service.RegistrosService;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author clovis.rodrigues
 */
@Controller
@RequestMapping("/registros")
public class RegistrosController {
    
    private static final String REGISTROS_URI = "registros/hoje";
    
    @Autowired
    private RegistrosNegocio registrosNegocio;
    
    @Autowired
    private RegistrosService registrosService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView hoje() {
        ModelAndView mv = new ModelAndView(REGISTROS_URI);
        RegistrosDto registros = registrosNegocio.filtrarRegistrosParaDataAtual();
        DiaDeTrabalho diaDeTrabalho = registrosNegocio.calcularTempoTrabalhadoPorJornadaTrabalho(registros);
        GraficoParametros graficoParametros = registrosNegocio.definirParametrosGrafico(diaDeTrabalho);
            if(registros.getHora().size() > 1 && registros.getHora().get(registros.getHora().size()-1).getId() == null ){
                registros.getHora().remove(registros.getHora().size()-1);
            }
        mv.addObject("diaDeTrabalho", diaDeTrabalho);
        mv.addObject("graficoParametros", graficoParametros);
        mv.addObject("registros", registros);
        return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> registroManual(@RequestBody String registro){
        String horaString[] = registro.split(":");
        int hora[] = new int[2];
        hora[0] = Integer.parseInt(horaString[0]);
        hora[1] = Integer.parseInt(horaString[1]);
        try {
            registrosService.inserirRegistroDiaManual(LocalTime.of(hora[0], hora[1]));
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(path = "/remover/{id}")
    public ResponseEntity<Object> removerRegistro(@PathVariable("id") long id){
        try {
        registrosService.removerRegistroDia(id);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}
