/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.BancoDeHorasDto;
import br.thirdimension.pontoaponto.dto.GraficoLinhaParametros;
import br.thirdimension.pontoaponto.negocio.MeusRegistrosNegocio;
import java.util.Optional;
import javax.ws.rs.QueryParam;
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
    public ModelAndView hoje(@QueryParam("pageSize") Optional<Integer> pageSize, @QueryParam("page") Optional<Integer> page) {
        ModelAndView mv = new ModelAndView(MEUS_BANCO_DE_HORAS_URI);
        BancoDeHorasDto bancoDeHorasDto = meusRegistrosNegocio.calcularBancoDeHoras();
        GraficoLinhaParametros graficoParametros = meusRegistrosNegocio.getGraficoParametros(); 
        GraficoLinhaParametros graficoParametrosNegativas = meusRegistrosNegocio.getGraficoParametrosNegativas(); 
        GraficoLinhaParametros graficoParametrosExtras  = meusRegistrosNegocio.getGraficoParametrosExtras();
        mv.addObject("bancoDeHoras", bancoDeHorasDto);
        mv.addObject("graficoParams", graficoParametros);
        mv.addObject("graficoExtra", graficoParametrosExtras);
        mv.addObject("graficoNegativa", graficoParametrosNegativas);
        return mv;
    }
    
}
