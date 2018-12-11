/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.negocio;

import br.thirdimension.pontoaponto.dto.DiaDeTrabalho;
import br.thirdimension.pontoaponto.dto.GraficoParametros;
import br.thirdimension.pontoaponto.dto.RegistroDiaDto;
import br.thirdimension.pontoaponto.dto.RegistrosDto;
import br.thirdimension.pontoaponto.model.RegistroDia;
import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.UsuarioRepository;
import br.thirdimension.pontoaponto.service.RegistrosService;
import br.thirdimension.pontoaponto.uteis.Conversores;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis.rodrigues
 */
@Component
public class RegistrosNegocio {
    
    private static final int HORA = 0;
    private static final int MINUTO = 1;
    private static final String HORAS_TRABALHADAS = "Horas trabalhadas";
    private static final String HORAS_RESTANTES = "Horas restantes";
    private static final String HORAS_EXTRAS = "Horas extras";
    private static final String COR_GRAFICO_HORAS_TRABALHADAS = "rgba(54, 162, 235, 0.2)";
    private static final String COR_BORDA_GRAFICO_HORAS_TRABALHADAS = "rgba(54, 162, 235, 1)";
    private static final String COR_GRAFICO_HORAS_EXTRAS = "rgba(1, 223, 58, 0.2)";
    private static final String COR_GRAFICO_BORDA_HORAS_EXTRAS = "rgba(4, 180, 49, 1)";
    private static final String COR_GRAFICO_HORAS_RESTANTES = "rgba(255, 206, 86, 0.2)";
    private static final String COR_GRAFICO_BORDA_HORAS_RESTANTES = "rgba(255, 206, 86, 1)";
    
    @Autowired
    private UsuarioSessao sessao;
    
    @Autowired
    private Conversores conversor;
    
    @Autowired
    private RegistrosService registrosService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Busca os registros de ponto do dia por usuário logado
     * @return - lista de registros
     */
    public RegistrosDto filtrarRegistrosParaDataAtual() {
        RegistrosDto registros = montarRegistrosDoDiaPorUsuario(sessao.getUsuario());
        if (necessitaIncluirRegistroParaCalculo(registros.getHora())) {
            adicionarRegistroParaCalculo(registros.getHora());
        }
        return registros;
    }
    
    /**
     * Calcula o tempo trabalhado sobre a jornada de trabalho
     * @param listaRegistros - registros de ponto do dia
     * @return - Objeto dia de trabalho definido
     */
    public DiaDeTrabalho calcularTempoTrabalhadoPorJornadaTrabalho(RegistrosDto registros) {
        LocalTime totalHorasTrabalhadas = getTotalSomaHorasTrabalhadas(registros);
        DiaDeTrabalho diaDeTrabalho = definirTempoTrabalhadoNoDiaDeTrabalho(totalHorasTrabalhadas);
        Long diferencaJornadaETotalTrabalhadoEmMinutos = 0L;
        if (sessao.getUsuario().getJornadaDeTrabalho().isAfter(diaDeTrabalho.getTempoTrabalhado())) {
            Calendar cal = Calendar.getInstance();
            diferencaJornadaETotalTrabalhadoEmMinutos = diaDeTrabalho.getTempoTrabalhado().until(sessao.getUsuario().getJornadaDeTrabalho(), ChronoUnit.MINUTES);
            diaDeTrabalho.setExtra(false);
            diaDeTrabalho.setLabelExtrasNegativas("Horas restantes: ");
            LocalTime diferencaJornadaETotalTrabalhado = LocalTime.now();
            diferencaJornadaETotalTrabalhado = diferencaJornadaETotalTrabalhado.plusMinutes(diferencaJornadaETotalTrabalhadoEmMinutos);
            //cal = definirHoraEncerramentoJornada(diferencaJornadaETotalTrabalhadoEmMinutos);
            diaDeTrabalho.setHoraSaidaFormatada(conversor.localTimeParaStringHora(diferencaJornadaETotalTrabalhado));
        } else {
            diferencaJornadaETotalTrabalhadoEmMinutos = diaDeTrabalho.getTempoTrabalhado().until(sessao.getUsuario().getJornadaDeTrabalho(), ChronoUnit.MINUTES);
            diaDeTrabalho.setExtra(true);
            diaDeTrabalho.setLabelExtrasNegativas("Horas extras: ");
            diaDeTrabalho.setHoraSaidaFormatada("Encerrada");
        }
        //int[] tempo = conversor.minutosParaHoraEminutos(diferencaJornadaETotalTrabalhadoEmMinutos);
        //Date data = conversor.definirHoraMinutoEmData(tempo[HORA], tempo[MINUTO]);
        LocalTime tempo = LocalTime.MIN;
        tempo = tempo.plusMinutes(diferencaJornadaETotalTrabalhadoEmMinutos);
        //int totalMinutos = conversor.converterHoraParaMinutos(tempo[HORA], tempo[MINUTO]); 
        diaDeTrabalho.setTempoFaltanteExtraFormatado(conversor.localTimeParaStringHora(tempo));
        diaDeTrabalho.setTempoFaltanteExtra(tempo);
        return diaDeTrabalho;
    }

    /**
     * Define parâmetros para apresentação do gráfico
     * @param diaDeTrabalho
     * @return 
     */
    public GraficoParametros definirParametrosGrafico(DiaDeTrabalho diaDeTrabalho){
        GraficoParametros graficoParametros = new GraficoParametros();
        graficoParametros.getLabels().add(HORAS_TRABALHADAS);
        graficoParametros.getDatasetsLabel().add(HORAS_TRABALHADAS);
        graficoParametros.getDataBackgroundColor().add(COR_GRAFICO_HORAS_TRABALHADAS);
        graficoParametros.getDataBorderColor().add(COR_BORDA_GRAFICO_HORAS_TRABALHADAS);
        if(diaDeTrabalho.isExtra()){
            graficoParametros.getLabels().add(HORAS_EXTRAS);
            graficoParametros.getDatasetsLabel().add(HORAS_EXTRAS);
            graficoParametros.getDataBackgroundColor().add(COR_GRAFICO_HORAS_EXTRAS);
            graficoParametros.getDataBorderColor().add(COR_GRAFICO_BORDA_HORAS_EXTRAS);
            graficoParametros.getDatasetsData().add(conversor.converterHoraParaMinutos(sessao.getUsuario().getJornadaDeTrabalho().getHour(), sessao.getUsuario().getJornadaDeTrabalho().getMinute()));
        }else{
            graficoParametros.getLabels().add(HORAS_RESTANTES);
            graficoParametros.getDatasetsLabel().add(HORAS_RESTANTES);
            graficoParametros.getDataBackgroundColor().add(COR_GRAFICO_HORAS_RESTANTES);
            graficoParametros.getDataBorderColor().add(COR_GRAFICO_BORDA_HORAS_RESTANTES);
            graficoParametros.getDatasetsData().add(conversor.converterHoraParaMinutos(diaDeTrabalho.getTempoTrabalhado().getHour(), diaDeTrabalho.getTempoTrabalhado().getMinute()));
        }
         graficoParametros.getDatasetsData().add(conversor.converterHoraParaMinutos(diaDeTrabalho.getTempoFaltanteExtra().getHour(), diaDeTrabalho.getTempoFaltanteExtra().getMinute()));
         return graficoParametros;
    }
     
    /**
     * Verifica a necessidade de incluir um registro com a data/hora atual para calcular a hora de encerramento da jornada de trabalho
     * @param listaRegistros
     * @return 
     */
    private boolean necessitaIncluirRegistroParaCalculo(List<RegistroDiaDto> listaRegistros) {
        return ((listaRegistros.size() % 2) != 0);
    }
    
    /**
     * Adiciona um registro com a data/hora atual na lista de registros para calcular a hora de encerramento da jornada de trabalho
     * @param listaRegistros
     * @return 
     */
    private void adicionarRegistroParaCalculo(List<RegistroDiaDto> listaRegistrosDia) {
        listaRegistrosDia.add(new RegistroDiaDto(LocalTime.now(), conversor.localTimeParaStringHora(LocalTime.now())));
    }
    
    /**
     * Soma o tempo trabalhado e retorna as horas e minutos em um array onde a primeira posição são as horas e a segunda os minutos trabalhados
     * @param listaRegistros
     * @return 
     */
    private LocalTime getTotalSomaHorasTrabalhadas(RegistrosDto registros) {
        int[] totalHorasTrabalhadas = new int[2];
        Duration diff = null;
        //List<Integer> horas = new ArrayList<>();
        //List<Integer> minutos = new ArrayList<>();
        LocalTime tempoTrabalhado = LocalTime.MIN;
        for (int index = 0; index < registros.getHora().size(); index += 2) {
            diff = Duration.between(registros.getHora().get(index).getHora(), registros.getHora().get(index + 1).getHora());
            int hours = (int) (diff.toMinutes() / 60);
           // horas.add(hours);
            int minutes = (int) (diff.toMinutes() % 60);
            //minutos.add(minutes);
            tempoTrabalhado = tempoTrabalhado.plusHours(hours);
            tempoTrabalhado = tempoTrabalhado.plusMinutes(minutes);
        }
      /*  horas.forEach((hora) -> {
            totalHorasTrabalhadas[HORA] += hora;
        });
        minutos.forEach((minuto) -> {
            totalHorasTrabalhadas[MINUTO] += minuto;
        });*/
        //return totalHorasTrabalhadas;
        return tempoTrabalhado;
    }
    
    /**
     * Define a hora de encerramento da jornada de trabalho 
     * @param minutos - Diferença em minutos entre tempo de jornada de trabalho e tempo trabalhado
     * @return 
     */
    private Calendar definirHoraEncerramentoJornada(int minutos) {
        int[] tempo = conversor.minutosParaHoraEminutos(minutos);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, tempo[HORA]);
        cal.add(Calendar.MINUTE, tempo[MINUTO]);
        return cal;
    }
    
    /**
     * Define horas e minutos trabalhados no objeto dia de trabalho
     * @param horas
     * @param minutos
     * @return 
     */
    private DiaDeTrabalho definirTempoTrabalhadoNoDiaDeTrabalho(LocalTime tempoTrabalhado) {
        DiaDeTrabalho diaDeTrabalho = new DiaDeTrabalho();
        diaDeTrabalho.setTempoTrabalhado(tempoTrabalhado);
        diaDeTrabalho.setTempoTrabalhadoFormatado(conversor.localTimeParaStringHora(tempoTrabalhado));
        diaDeTrabalho.setJornadaDeTrabalho("Jornada de trabalho: " + conversor.localTimeParaStringHora(sessao.getUsuario().getJornadaDeTrabalho()));
        return diaDeTrabalho;
    }
    
    /**
     * Importa registros do rep para a aplicação
     */
   /* private void importarRegistros() {
      List<RegistrosDto> registrosREP = registrosService.buscarListaDeRegistrosREP();
      registrosService.importarRegistros(registrosREP);
    }*/
    
    /**
     * Busca os registros na base e monta dto
     * @param usuario
     * @return - lista de registros
     */
    private RegistrosDto montarRegistrosDoDiaPorUsuario(Usuario usuario){
        Registros registro =  registrosService.buscarUltimoRegistroInserido(usuario);
        if(registro == null){
            registro = new Registros();
            try {
                registro = registrosService.inserirRegistroManual(LocalDate.now());
                registro.setRegistroDia(new ArrayList<>());
            } catch (Exception ex) {
                Logger.getLogger(RegistrosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<RegistroDiaDto> registrosDiaDto = converterListaEntidadeEmListaDto(registro.getRegistroDia());
        RegistrosDto registrosDto = new RegistrosDto(registro.getID(), conversor.localDateParaString(registro.getDataRegistro()), registro.getDataRegistro(), registrosDiaDto);
        return registrosDto;
    }
    
    private List<RegistroDiaDto> converterListaEntidadeEmListaDto(List<RegistroDia> registrosDia){
        List<RegistroDiaDto> registrosDiaDto = new ArrayList<>();
        for(RegistroDia registroDia : registrosDia){
            registrosDiaDto.add(new RegistroDiaDto(registroDia.getId(), registroDia.getHora(), conversor.localTimeParaStringHora(registroDia.getHora())));
        }
        return registrosDiaDto;
    }
}
