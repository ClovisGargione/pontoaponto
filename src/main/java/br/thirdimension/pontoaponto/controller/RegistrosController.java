/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.api.client.REPApiClient;
import br.thirdimension.pontoaponto.configuracao.ResourceOwner;
import br.thirdimension.pontoaponto.dto.DiaDeTrabalho;
import br.thirdimension.pontoaponto.dto.GraficoParametros;
import br.thirdimension.pontoaponto.dto.Registros;
import br.thirdimension.pontoaponto.exception.REPException;
import br.thirdimension.pontoaponto.model.RegistrosGerais;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.RegistrosRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    
    private static final String HORAS_TRABALHADAS = "Horas trabalhadas";
    private static final String HORAS_RESTANTES = "Horas restantes";
    private static final String HORAS_EXTRAS = "Horas extras";

    @Autowired
    private REPApiClient repApiClient;

    @Autowired
    private RegistrosRepository registrosRepository;

    private DiaDeTrabalho diaDeTrabalho = new DiaDeTrabalho();
    
    private GraficoParametros graficoParametros = new GraficoParametros();

    private String labelExtrasNegativas = "";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cadastro() {
        buscarListaDeRegistrosREP();
        limparParametrosGrafico();
        ModelAndView mv = new ModelAndView("registros/hoje");
        List<Registros> registros = filtrarRegistrosParaDataAtual();
        mv.addObject("diaDeTrabalho", diaDeTrabalho);
        mv.addObject("tempoRestanteExtra", labelExtrasNegativas);
        mv.addObject("graficoParametros", graficoParametros);
        mv.addObject("registros", registros);
        return mv;
    }

    private List<Registros> buscarListaDeRegistrosREP() {
        List<Registros> registros = new ArrayList<>();
        try {
            registros = repApiClient.registros();
        } catch (REPException ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        importarRegistros(registros);
        return registros;
    }

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ResourceOwner resourceOwner = (ResourceOwner) auth.getPrincipal();
        Usuario usuario = resourceOwner.getUsuario();
        return usuario;
    }

    private boolean necessitaIncluirRegistroParaCalculo(List<Registros> listaRegistros) {
        return ((listaRegistros.size() % 2) != 0);
    }

    private void adicionarRegistroParaCalculo(List<Registros> listaRegistros) {
        Usuario usuario = getUsuarioLogado();
        listaRegistros.add(new Registros("", usuario.getPis(), "", new Date(), ""));
    }

    private int getTotalHorasTrabalhadas(List<Registros> listaRegistros) {
        int totalHorasTrabalhadas = 0;
        Duration diff = null;
        List<Integer> horas = new ArrayList<>();
        for (int index = 0; index < listaRegistros.size(); index += 2) {
            diff = Duration.between(listaRegistros.get(index).getDataHora().toInstant(), listaRegistros.get(index + 1).getDataHora().toInstant());
            int hours = (int) (diff.toMinutes() / 60);
            horas.add(hours);
        }
        for (Integer hora : horas) {
            totalHorasTrabalhadas += hora;
        }
        return totalHorasTrabalhadas;
    }
  
    private int getTotalMinutosTrabalhados(List<Registros> listaRegistros) {
        int totalminutosTrabalhados = 0;
        Duration diff = null;
        List<Integer> minutos = new ArrayList<>();
        for (int index = 0; index < listaRegistros.size(); index += 2) {
            diff = Duration.between(listaRegistros.get(index).getDataHora().toInstant(), listaRegistros.get(index + 1).getDataHora().toInstant());
            int minutes = (int) (diff.toMinutes() % 60);
            minutos.add(minutes);
        }
        for (Integer minuto : minutos) {
            totalminutosTrabalhados += minuto;
        }
        return totalminutosTrabalhados;
    }

    private void calcularTempoTralhado(int horas, int minutos) {
        if (minutos > 59) {
            horas += (int) (minutos / 60);
            minutos = (int) (minutos % 60);
        }
        Date data = definirHoraMinutoEmData(horas, minutos);
        diaDeTrabalho.setTempoTrabalhadoEmMinutos((horas * 60) + minutos);
        diaDeTrabalho.setTempoTralhado(dateToString(data));
        graficoParametros.getLabels().add(HORAS_TRABALHADAS);
        graficoParametros.getDatasetsLabel().add(HORAS_TRABALHADAS);
        graficoParametros.getDataBackgroundColor().add("rgba(54, 162, 235, 0.2)");
        graficoParametros.getDataBorderColor().add("rgba(54, 162, 235, 1)");
    }

    private void calcularTempoTrabalhadoXjornadaTrabalho(int horas, int minutos) {
        int totalJornadaDeTrabalho = (8 * 60) + 30;
        int totalTrabalhado = (horas * 60) + minutos;
        Calendar cal = Calendar.getInstance();
        int t, h, m = 0;
        if (totalJornadaDeTrabalho > totalTrabalhado) {
            t = totalJornadaDeTrabalho - totalTrabalhado;
            diaDeTrabalho.setExtra(false);
            labelExtrasNegativas = "Horas restantes: ";
            graficoParametros.getLabels().add(HORAS_RESTANTES);
            graficoParametros.getDatasetsLabel().add(HORAS_RESTANTES);
            graficoParametros.getDataBackgroundColor().add("rgba(255, 206, 86, 0.2)");
            graficoParametros.getDataBorderColor().add("rgba(255, 206, 86, 1)");
            graficoParametros.getDatasetsData().add(diaDeTrabalho.getTempoTrabalhadoEmMinutos());
            cal = definirHoraEncerramentoJornada(t);
            diaDeTrabalho.setHoraSaida(dateToString(cal.getTime()));
        } else {
            t = totalTrabalhado - totalJornadaDeTrabalho;
            diaDeTrabalho.setExtra(true);
            labelExtrasNegativas = "Horas extras: ";
            graficoParametros.getLabels().add(HORAS_EXTRAS);
            graficoParametros.getDatasetsLabel().add(HORAS_EXTRAS);
            graficoParametros.getDataBackgroundColor().add("rgba(1, 223, 58, 0.2)");
            graficoParametros.getDataBorderColor().add("rgba(4, 180, 49, 1)");
            graficoParametros.getDatasetsData().add(totalJornadaDeTrabalho);
            diaDeTrabalho.setHoraSaida("Encerrada");
        }
        h = (int) (t / 60);
        m = (int) (t % 60);
        
        Date data = definirHoraMinutoEmData(h, m);
        diaDeTrabalho.setTempoFaltanteExtraEmMinutos((h * 60) + m);
        diaDeTrabalho.setTempoFaltanteExtra(dateToString(data));
        graficoParametros.getDatasetsData().add(diaDeTrabalho.getTempoFaltanteExtraEmMinutos());
    }

    private Calendar definirHoraEncerramentoJornada(int minutos) {
        int h = (int) (minutos / 60);
        int m = (int) (minutos % 60);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, h);
        cal.add(Calendar.MINUTE, m);
        return cal;
    }

    private List<Registros> filtrarRegistrosParaDataAtual() {
        List<Registros> listaRegistros = new ArrayList<>();
        List<RegistrosGerais> registrosGerais = registrosRepository.findAll();
        Usuario usuario = getUsuarioLogado();
        Date hoje = new Date();
        for (RegistrosGerais gerais : registrosGerais) {
            if (gerais.getPIS().equals(usuario.getPis()) && gerais.getDataHoraRegistro().getDay() == hoje.getDay() && gerais.getDataHoraRegistro().getMonth() == hoje.getMonth() && gerais.getDataHoraRegistro().getYear() == hoje.getYear()) {
                listaRegistros.add(new Registros(dateToString(gerais.getDataHoraRegistro()), gerais.getPIS(), gerais.getNSR(), gerais.getDataHoraRegistro(), dateToString(gerais.getDataHoraRegistro())));
            }
        }
        Collections.sort(listaRegistros);
        if (necessitaIncluirRegistroParaCalculo(listaRegistros)) {
            adicionarRegistroParaCalculo(listaRegistros);
        }

        int totalHorasTrabalhadas = getTotalHorasTrabalhadas(listaRegistros);
        int totalminutosTrabalhadas = getTotalMinutosTrabalhados(listaRegistros);

        calcularTempoTralhado(totalHorasTrabalhadas, totalminutosTrabalhadas);

        calcularTempoTrabalhadoXjornadaTrabalho(totalHorasTrabalhadas, totalminutosTrabalhadas);

        return listaRegistros;
    }

    private void importarRegistros(List<Registros> registrosREP) {
        List<RegistrosGerais> registrosGerais = registrosRepository.findAll();
        List<String> nsr = new ArrayList<>();
        for (RegistrosGerais gerais : registrosGerais) {
            nsr.add(gerais.getNSR());
        }
        for (Registros registros : registrosREP) {
            if (!nsr.contains(registros.getNsr())) {
                registrosRepository.save(new RegistrosGerais(registros.getDataHora(), registros.getPis(), registros.getNsr()));
            }
        }
    }

    private String dateToString(Date data) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dataString = format.format(data);
        return dataString;
    }

    private Date stringToDate(String dataString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = null;
        try {
            data = new Date(format.parse(dataString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    private Date definirHoraMinutoEmData(int hora, int minuto) {
        Date data = new Date();
        data.setMinutes(minuto);
        data.setHours(hora);
        return data;
    }
    
    private void limparParametrosGrafico(){
        graficoParametros.getDataBackgroundColor().clear();
        graficoParametros.getDataBorderColor().clear();
        graficoParametros.getDatasetsData().clear();
        graficoParametros.getDatasetsLabel().clear();
        graficoParametros.getLabels().clear();
    }
}
