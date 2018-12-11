/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.MeusRegistrosDto;
import br.thirdimension.pontoaponto.dto.PagerModel;
import br.thirdimension.pontoaponto.dto.RegistrosDto;
import br.thirdimension.pontoaponto.exception.PesquisarException;
import br.thirdimension.pontoaponto.negocio.MeusRegistrosNegocio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author clovis.rodrigues
 */
@Controller
@RequestMapping("/meusregistros")
public class MeusRegistrosController {
    
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 20;
    private static final int[] PAGE_SIZES = { 5, 10};

    private static final String MEUS_REGISTROS_URI = "registros/meus-registros";

    @Autowired
    private MeusRegistrosNegocio meusRegistrosNegocio;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView hoje(@QueryParam("pageSize") Optional<Integer> pageSize, @QueryParam("page") Optional<Integer> page) {
        ModelAndView mv = new ModelAndView(MEUS_REGISTROS_URI);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        List<RegistrosDto> registrosGerais = meusRegistrosNegocio.buscarListaDeRegistrosAgrupadaPorData(new PageRequest(evalPage, evalPageSize, Sort.Direction.DESC, "dataRegistro"));
        PagerModel pager = meusRegistrosNegocio.getPagerModel();
        mv.addObject("registros", registrosGerais);
        mv.addObject("selectedPageSize", evalPageSize);
        mv.addObject("pageSizes", PAGE_SIZES);
        mv.addObject("pager", pager);
        return mv;
    }

    @GetMapping("/pesquisar")
    public String pesquisarRegistros(Model model, @QueryParam("dataInicial") Optional<Long> dataInicial, @QueryParam("dataFinal") Optional<Long> dataFinal, @QueryParam("pageSize") Optional<Integer> pageSize, @QueryParam("page") Optional<Integer> page) {
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        List<RegistrosDto> registrosGerais = new ArrayList<>();
        try {
            registrosGerais = meusRegistrosNegocio.buscarListaDeRegistrosFiltrada(dataInicial.orElse(0L), dataFinal.orElse(0L), new PageRequest(evalPage, evalPageSize, Sort.Direction.DESC, "dataRegistro"));
        } catch (PesquisarException ex) {
            Logger.getLogger(MeusRegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PagerModel pager = meusRegistrosNegocio.getPagerModel();
        model.addAttribute("registros", registrosGerais);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);
        return MEUS_REGISTROS_URI + " :: resultado-pesquisa";
    }

}
