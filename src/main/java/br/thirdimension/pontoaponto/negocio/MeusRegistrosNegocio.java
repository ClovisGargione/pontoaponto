/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.negocio;

import br.thirdimension.pontoaponto.dto.DadosBarraDeProgressoDto;
import br.thirdimension.pontoaponto.dto.PagerModel;
import br.thirdimension.pontoaponto.dto.RegistroDiaDto;
import br.thirdimension.pontoaponto.dto.RegistrosDto;
import br.thirdimension.pontoaponto.exception.PesquisarException;
import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.RegistrosDia;
import br.thirdimension.pontoaponto.service.RegistrosService;
import br.thirdimension.pontoaponto.uteis.Conversores;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis
 */
@Component
public class MeusRegistrosNegocio {
    
    private static final int BUTTONS_TO_SHOW = 3;

    @Autowired
    private Conversores conversores;
    
    @Autowired
    private UsuarioSessao usuarioSessao;

    @Autowired
    private RegistrosService registrosService;
    
    private PagerModel pagerModel;
    
    public List<RegistrosDto> buscarListaDeRegistrosAgrupadaPorData(PageRequest pageRequest) {
        Page<Registros> registros = registrosService.buscarListaDeRegistros(pageRequest);
        List<RegistrosDto> meusRegistros = new ArrayList<>();
        meusRegistros = converterEntidadeRegistrosParaDto(registros.getContent());
        this.pagerModel = new PagerModel(registros.getTotalPages(), registros.getNumber(), BUTTONS_TO_SHOW, registros.getTotalPages(), registros.getNumber());
        return meusRegistros;
    }
    
    public PagerModel getPagerModel(){
        return this.pagerModel;
    }
    
    public List<RegistrosDto> buscarListaDeRegistrosFiltrada(Long dataInicialLong, Long dataFinalLong, boolean incompletos, PageRequest pageRequest) throws PesquisarException{
        List<RegistrosDto> meusRegistrosDto = new ArrayList<>();
        Page<Registros> meusRegistros = Page.empty();
        if(dataInicialLong != 0L && dataFinalLong != 0L && !incompletos){
            LocalDate dataInicial = Instant.ofEpochMilli(dataInicialLong).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataFinal = Instant.ofEpochMilli(dataFinalLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaDeRegistrosEntreDatasPorUsuario(dataInicial, dataFinal, pageRequest);
        }
        if(dataInicialLong != 0L && dataFinalLong == 0L && !incompletos){
            LocalDate dataInicial = Instant.ofEpochMilli(dataInicialLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaDeRegistrosApartirDaData(dataInicial, pageRequest);
        }
        if(dataInicialLong == 0L && dataFinalLong != 0L && !incompletos){
            LocalDate dataFinal = Instant.ofEpochMilli(dataFinalLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaDeRegistrosAteAData(dataFinal, pageRequest);
        }
        if(dataInicialLong == 0L && dataFinalLong == 0L && !incompletos){
            meusRegistros = registrosService.buscarListaDeRegistros(pageRequest);
        }
        if(dataInicialLong != 0L && dataFinalLong != 0L && incompletos){
            LocalDate dataInicial = Instant.ofEpochMilli(dataInicialLong).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataFinal = Instant.ofEpochMilli(dataFinalLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaDeRegistrosIncompletosEntreDatas(dataInicial, dataFinal, pageRequest);
        }
        if(dataInicialLong != 0L && dataFinalLong == 0L && incompletos){
            LocalDate dataInicial = Instant.ofEpochMilli(dataInicialLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaRegistrosIncompletosApartirDaData(dataInicial, pageRequest);
        }
        if(dataInicialLong == 0L && dataFinalLong != 0L && incompletos){
            LocalDate dataFinal = Instant.ofEpochMilli(dataFinalLong).atZone(ZoneId.systemDefault()).toLocalDate();
            meusRegistros = registrosService.buscarListaRegistrosIncompletosAteAData(dataFinal, pageRequest);
        }
        if(dataInicialLong == 0L && dataFinalLong == 0L && incompletos){
            meusRegistros = registrosService.buscarListaDeRegistrosIncompletos(pageRequest);
        }
        try{
            meusRegistrosDto = converterEntidadeRegistrosParaDto(meusRegistros.getContent());
            this.pagerModel = new PagerModel(meusRegistros.getTotalPages(), meusRegistros.getNumber(), BUTTONS_TO_SHOW, meusRegistros.getTotalPages(), meusRegistros.getNumber());
        }catch(Exception p){
            Logger.getLogger(MeusRegistrosNegocio.class.getName()).log(Level.SEVERE, null, p);
            throw new PesquisarException("Não foi possível pesquisar os registros!");
        }
        return meusRegistrosDto;
    }
/*
    private List<MeusRegistrosDto> agruparRegistros(List<Registros> registros) {
        List<MeusRegistrosDto> meusRegistros = new ArrayList<>();
        List<MeusRegistrosDto> meusRegistros_ = new ArrayList<>();
        MeusRegistrosDto meuRegistro = new MeusRegistrosDto();
        List<String> horaRegistro = new ArrayList<>();
        for (Registros registro : registros) {
            if (meuRegistro.getDataHoraFormatada() == null || !meuRegistro.getDataHoraFormatada().equals(conversores.dataParaString(registro.getDataHoraRegistro()))) {
                meuRegistro.setDataHoraFormatada(conversores.dataParaString(registro.getDataHoraRegistro()));
                meusRegistros.add(new MeusRegistrosDto(meuRegistro.getDataHoraFormatada(), new ArrayList<>()));
            }
        }
        for (MeusRegistrosDto m : meusRegistros) {
            for (Registros registro_ : registros) {
                if (m.getDataHoraFormatada().equals(conversores.dataParaString(registro_.getDataHoraRegistro()))) {
                    horaRegistro.add(conversores.dataParaStringHora(registro_.getDataHoraRegistro()));
                }
            }
            Collections.sort(horaRegistro);
            m.getHoraDoRegistro().addAll(horaRegistro);
            meusRegistros_.add(m);
            horaRegistro.clear();
        }
        return meusRegistros_;
    }
*/
    private DadosBarraDeProgressoDto calcularDadosBarraDeProgresso(List<RegistroDiaDto> registrosDiaDto){
        DadosBarraDeProgressoDto dadosBarraDeProgressoDto = new DadosBarraDeProgressoDto(0, 0, 0);
        if(registrosDiaDto.size() % 2 != 0){
            dadosBarraDeProgressoDto.setFaltante(100);
        }else{
            int jornadaDeTrabalho = conversores.converterHoraParaMinutos(usuarioSessao.getUsuario().getJornadaDeTrabalho().getHour(), usuarioSessao.getUsuario().getJornadaDeTrabalho().getMinute());
            int trabalhadas = getTotalSomaHorasTrabalhadasEmMinutos(registrosDiaDto);
            dadosBarraDeProgressoDto.setHorasTrabalhadas(trabalhadas);
            if(jornadaDeTrabalho > trabalhadas){
                int faltantes = jornadaDeTrabalho - trabalhadas;
                dadosBarraDeProgressoDto.setFaltante(faltantes);
            }else{
                int extra = trabalhadas - jornadaDeTrabalho;
                dadosBarraDeProgressoDto.setExtra(extra);
            }
        }
        return dadosBarraDeProgressoDto;        
    }
    
    private int getTotalSomaHorasTrabalhadasEmMinutos(List<RegistroDiaDto> registrosDiaDto) {
        Duration diff = null;
        LocalTime tempoTrabalhado = LocalTime.MIN;
        for (int index = 0; index < registrosDiaDto.size(); index += 2) {
            diff = Duration.between(registrosDiaDto.get(index).getHora(), registrosDiaDto.get(index + 1).getHora());
            int hours = (int) (diff.toMinutes() / 60);
            int minutes = (int) (diff.toMinutes() % 60);
            tempoTrabalhado = tempoTrabalhado.plusHours(hours);
            tempoTrabalhado = tempoTrabalhado.plusMinutes(minutes);
        }
        return conversores.converterHoraParaMinutos(tempoTrabalhado.getHour(), tempoTrabalhado.getMinute());
    }

    private List<RegistrosDto> converterEntidadeRegistrosParaDto(List<Registros> registros) {
        List<RegistroDiaDto> registrosDiaDto = null;
        List<RegistrosDto> registrosDto = new ArrayList<>();
        DadosBarraDeProgressoDto barraDeProgressoDto = new DadosBarraDeProgressoDto();
        RegistrosDto registroDto = new RegistrosDto();
        for(Registros registro : registros){
            registrosDiaDto = new ArrayList<>();
            registrosDiaDto = converterListaRegistroDiaParaDto(registro.getRegistrosDia());
            Collections.sort(registrosDiaDto);
            barraDeProgressoDto = calcularDadosBarraDeProgresso(registrosDiaDto);
            registroDto = new RegistrosDto(registro.getID(), conversores.localDateParaString(registro.getDataRegistro()), registro.getDataRegistro(), registrosDiaDto);
            registroDto.setBarraDeProgressoDto(barraDeProgressoDto);
            registrosDto.add(registroDto);
        }      
        return registrosDto;
    }
    
    private List<RegistroDiaDto> converterListaRegistroDiaParaDto(List<RegistrosDia> registrosDia){
        List<RegistroDiaDto> registrosDiaDto = new ArrayList<>();
        for(RegistrosDia registroDia : registrosDia){
            registrosDiaDto.add(new RegistroDiaDto(registroDia.getId(), registroDia.getHora(), conversores.localTimeParaStringHora(registroDia.getHora())));
        }
        return registrosDiaDto;
    }
}
