/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.DiaDeTrabalhoDto;
import br.thirdimension.pontoaponto.dto.GraficoParametrosDto;
import br.thirdimension.pontoaponto.dto.RegistroManualDto;
import br.thirdimension.pontoaponto.dto.RegistrosDto;
import br.thirdimension.pontoaponto.dto.TarefaDto;
import br.thirdimension.pontoaponto.negocio.RegistrosNegocio;
import br.thirdimension.pontoaponto.service.RegistrosService;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        DiaDeTrabalhoDto diaDeTrabalho = registrosNegocio.calcularTempoTrabalhadoPorJornadaTrabalho(registros);
        GraficoParametrosDto graficoParametros = registrosNegocio.definirParametrosGrafico(diaDeTrabalho);
        if (registros.getHora().size() > 1 && registros.getHora().get(registros.getHora().size() - 1).getId() == null) {
            registros.getHora().remove(registros.getHora().size() - 1);
        }
        mv.addObject("diaDeTrabalho", diaDeTrabalho);
        mv.addObject("graficoParametros", graficoParametros);
        mv.addObject("diaCorrente", true);
        mv.addObject("cardLabel", "Registros de hoje");
        mv.addObject("registros", registros);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> registroManual(@RequestBody RegistroManualDto registro) {
        try {
            registrosService.inserirRegistroDiaManual(registro.getRegistroId(), LocalTime.parse(registro.getRegistroDia()));
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/remover/{id}")
    public ResponseEntity<Object> removerRegistro(@PathVariable("id") long id) {
        try {
            registrosService.removerRegistroDia(id);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView(REGISTROS_URI);
        RegistrosDto registros = registrosNegocio.filtrarRegistrosPorId(id);
        DiaDeTrabalhoDto diaDeTrabalho = registrosNegocio.calcularTempoTrabalhadoPorJornadaTrabalho(registros);
        GraficoParametrosDto graficoParametros = registrosNegocio.definirParametrosGrafico(diaDeTrabalho);
        if (registros.getHora().size() > 1 && registros.getHora().get(registros.getHora().size() - 1).getId() == null) {
            registros.getHora().remove(registros.getHora().size() - 1);
        }
        mv.addObject("diaDeTrabalho", diaDeTrabalho);
        mv.addObject("graficoParametros", graficoParametros);
        mv.addObject("diaCorrente", false);
        mv.addObject("cardLabel", "Registros de " + registros.getDataRegistroFormatada());
        mv.addObject("registros", registros);
        return mv;
    }
    
    @RequestMapping(path = "/tarefa", method = RequestMethod.POST)
    public String registrarTarefa(Model model,@Valid TarefaDto tarefaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tarefa", tarefaDto);
        }
        tarefaDto.setTempo(LocalTime.parse(tarefaDto.getTempoFormatado()));
        try {
            registrosService.inserirTarefa(tarefaDto);
        } catch (Exception ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
       RegistrosDto registros = registrosNegocio.filtrarRegistrosParaDataAtual();
       model.addAttribute("registros", registros);
        return REGISTROS_URI + " :: lista-tarefas";
    }
    
    @RequestMapping(path = "/tarefa/{id}", method = RequestMethod.GET)
    public String removerTarefa(Model model, @PathVariable("id") long id){
        registrosService.removerTarefa(id);
        RegistrosDto registros = registrosNegocio.filtrarRegistrosParaDataAtual();
        model.addAttribute("registros", registros);
        return REGISTROS_URI + " :: lista-tarefas";
    }
}
