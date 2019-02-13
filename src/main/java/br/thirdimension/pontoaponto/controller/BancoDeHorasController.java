/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.BancoDeHorasDto;
import br.thirdimension.pontoaponto.dto.GraficoLinhaParametrosDto;
import br.thirdimension.pontoaponto.negocio.MeusRegistrosNegocio;
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
@RequestMapping("/bancodehoras")
public class BancoDeHorasController {
    
    private static final String MEUS_BANCO_DE_HORAS_URI = "registros/banco-de-horas";
    
    @Autowired
    private MeusRegistrosNegocio meusRegistrosNegocio;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView bancoDeHoras() {
        ModelAndView mv = new ModelAndView(MEUS_BANCO_DE_HORAS_URI);
        BancoDeHorasDto bancoDeHorasDto = meusRegistrosNegocio.calcularBancoDeHoras();
        GraficoLinhaParametrosDto graficoParametros = meusRegistrosNegocio.calcularDadosGrafico();
        mv.addObject("bancoDeHoras", bancoDeHorasDto);
        mv.addObject("graficoParams", graficoParametros);
        return mv;
    }
    
}
