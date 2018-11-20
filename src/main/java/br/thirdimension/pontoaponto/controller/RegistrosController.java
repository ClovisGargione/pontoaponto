/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.DiaDeTrabalho;
import br.thirdimension.pontoaponto.dto.GraficoParametros;
import br.thirdimension.pontoaponto.dto.Registros;
import br.thirdimension.pontoaponto.negocio.RegistrosNegocio;
import br.thirdimension.pontoaponto.service.RegistrosService;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        List<Registros> registros = registrosNegocio.filtrarRegistrosParaDataAtual();
        DiaDeTrabalho diaDeTrabalho = registrosNegocio.calcularTempoTrabalhadoPorJornadaTrabalho(registros);
        GraficoParametros graficoParametros = registrosNegocio.definirParametrosGrafico(diaDeTrabalho);
        mv.addObject("diaDeTrabalho", diaDeTrabalho);
        mv.addObject("graficoParametros", graficoParametros);
        mv.addObject("registros", registros);
        return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> registroManual(@RequestBody Date registro){
        try {
            registrosService.inserirRegistroManual(registro);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}
