/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.model.RegistrosGerais;
import br.thirdimension.pontoaponto.service.RegistrosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author clovis.rodrigues
 */
@Controller
@RequestMapping("/meusregistros")
public class MeusRegistros {
    
    private static final String MEUS_REGISTROS_URI = "registros/meus-registros";
    
    @Autowired
    private RegistrosService registrosService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView hoje() {
        ModelAndView mv = new ModelAndView(MEUS_REGISTROS_URI);
        List<RegistrosGerais> registrosGerais = registrosService.buscarListaDeRegistrosDoUsuario();
        mv.addObject("registros", registrosGerais);
        return mv;
    }
    
}
