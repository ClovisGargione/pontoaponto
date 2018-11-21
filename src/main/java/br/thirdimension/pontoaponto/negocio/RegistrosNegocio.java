/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.negocio;

import br.thirdimension.pontoaponto.dto.DiaDeTrabalho;
import br.thirdimension.pontoaponto.dto.GraficoParametros;
import br.thirdimension.pontoaponto.dto.Registros;
import br.thirdimension.pontoaponto.model.RegistrosGerais;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.RegistrosRepository;
import br.thirdimension.pontoaponto.service.RegistrosService;
import br.thirdimension.pontoaponto.uteis.Conversores;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
    private RegistrosRepository registrosRepository;
    
    /**
     * Importa e filtra os registros de ponto do dia por usuário logado
     * @return - lista de registros
     */
    public List<Registros> filtrarRegistrosParaDataAtual() {
        importarRegistros();
        List<Registros> listaRegistros = montarListaDeRegistrosDoDiaPorUsuario(sessao.getUsuario());
        if (necessitaIncluirRegistroParaCalculo(listaRegistros)) {
            adicionarRegistroParaCalculo(listaRegistros);
        }
        return listaRegistros;
    }
    
    /**
     * Calcula o tempo trabalhado sobre a jornada de trabalho
     * @param listaRegistros - registros de ponto do dia
     * @return - Objeto dia de trabalho definido
     */
    public DiaDeTrabalho calcularTempoTrabalhadoPorJornadaTrabalho(List<Registros> listaRegistros) {
        int[] totalHorasTrabalhadas = getTotalSomaHorasTrabalhadas(listaRegistros);
        DiaDeTrabalho diaDeTrabalho = definirTempoTrabalhadoNoDiaDeTrabalho(totalHorasTrabalhadas[HORA], totalHorasTrabalhadas[MINUTO]);
        int diferencaJornadaETotalTrabalhadoEmMinutos = 0;
        if (sessao.getUsuario().getJornadaDeTrabalhoEmMinutos() > diaDeTrabalho.getTempoTrabalhadoEmMinutos()) {
            Calendar cal = Calendar.getInstance();
            diferencaJornadaETotalTrabalhadoEmMinutos = sessao.getUsuario().getJornadaDeTrabalhoEmMinutos() - diaDeTrabalho.getTempoTrabalhadoEmMinutos();
            diaDeTrabalho.setExtra(false);
            diaDeTrabalho.setLabelExtrasNegativas("Horas restantes: ");
            cal = definirHoraEncerramentoJornada(diferencaJornadaETotalTrabalhadoEmMinutos);
            diaDeTrabalho.setHoraSaida(conversor.dataParaStringHora(cal.getTime()));
        } else {
            diferencaJornadaETotalTrabalhadoEmMinutos = diaDeTrabalho.getTempoTrabalhadoEmMinutos() - sessao.getUsuario().getJornadaDeTrabalhoEmMinutos();
            diaDeTrabalho.setExtra(true);
            diaDeTrabalho.setLabelExtrasNegativas("Horas extras: ");
            diaDeTrabalho.setHoraSaida("Encerrada");
        }
        int[] tempo = conversor.minutosParaHoraEminutos(diferencaJornadaETotalTrabalhadoEmMinutos);
        Date data = conversor.definirHoraMinutoEmData(tempo[HORA], tempo[MINUTO]);
        int totalMinutos = conversor.converterHoraParaMinutos(tempo[HORA], tempo[MINUTO]); 
        diaDeTrabalho.setTempoFaltanteExtraEmMinutos(totalMinutos);
        diaDeTrabalho.setTempoFaltanteExtra(conversor.dataParaStringHora(data));
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
            graficoParametros.getDatasetsData().add(sessao.getUsuario().getJornadaDeTrabalhoEmMinutos());
        }else{
            graficoParametros.getLabels().add(HORAS_RESTANTES);
            graficoParametros.getDatasetsLabel().add(HORAS_RESTANTES);
            graficoParametros.getDataBackgroundColor().add(COR_GRAFICO_HORAS_RESTANTES);
            graficoParametros.getDataBorderColor().add(COR_GRAFICO_BORDA_HORAS_RESTANTES);
            graficoParametros.getDatasetsData().add(diaDeTrabalho.getTempoTrabalhadoEmMinutos());
        }
         graficoParametros.getDatasetsData().add(diaDeTrabalho.getTempoFaltanteExtraEmMinutos());
         return graficoParametros;
    }
     
    /**
     * Verifica a necessidade de incluir um registro com a data/hora atual para calcular a hora de encerramento da jornada de trabalho
     * @param listaRegistros
     * @return 
     */
    private boolean necessitaIncluirRegistroParaCalculo(List<Registros> listaRegistros) {
        return ((listaRegistros.size() % 2) != 0);
    }
    
    /**
     * Adiciona um registro com a data/hora atual na lista de registros para calcular a hora de encerramento da jornada de trabalho
     * @param listaRegistros
     * @return 
     */
    private List<Registros> adicionarRegistroParaCalculo(List<Registros> listaRegistros) {
        listaRegistros.add(new Registros(sessao.getUsuario().getPis(), new Date()));
        return listaRegistros;
    }
    
    /**
     * Soma o tempo trabalhado e retorna as horas e minutos em um array onde a primeira posição são as horas e a segunda os minutos trabalhados
     * @param listaRegistros
     * @return 
     */
    private int[] getTotalSomaHorasTrabalhadas(List<Registros> listaRegistros) {
        int[] totalHorasTrabalhadas = new int[2];
        Duration diff = null;
        List<Integer> horas = new ArrayList<>();
        List<Integer> minutos = new ArrayList<>();
        for (int index = 0; index < listaRegistros.size(); index += 2) {
            diff = Duration.between(listaRegistros.get(index).getDataHora().toInstant(), listaRegistros.get(index + 1).getDataHora().toInstant());
            int hours = (int) (diff.toMinutes() / 60);
            horas.add(hours);
            int minutes = (int) (diff.toMinutes() % 60);
            minutos.add(minutes);
        }
        horas.forEach((hora) -> {
            totalHorasTrabalhadas[HORA] += hora;
        });
        minutos.forEach((minuto) -> {
            totalHorasTrabalhadas[MINUTO] += minuto;
        });
        return totalHorasTrabalhadas;
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
    private DiaDeTrabalho definirTempoTrabalhadoNoDiaDeTrabalho(int horas, int minutos) {
        DiaDeTrabalho diaDeTrabalho = new DiaDeTrabalho();
        int[] horaAjustada = conversor.ajustarMinutos(horas, minutos);
        Date data = conversor.definirHoraMinutoEmData(horaAjustada[HORA], horaAjustada[MINUTO]);
        int totalMinutos = conversor.converterHoraParaMinutos(horaAjustada[HORA], horaAjustada[MINUTO]); 
        diaDeTrabalho.setTempoTrabalhadoEmMinutos(totalMinutos);
        diaDeTrabalho.setTempoTrabalhado(conversor.dataParaStringHora(data));
        int[] jornadaDeTrabalho = conversor.minutosParaHoraEminutos(sessao.getUsuario().getJornadaDeTrabalhoEmMinutos());
        Date jornadaDeTrabalhoData = conversor.definirHoraMinutoEmData(jornadaDeTrabalho[HORA], jornadaDeTrabalho[MINUTO]);
        diaDeTrabalho.setJornadaDeTrabalho("Jornada de trabalho: " + conversor.dataParaStringHora(jornadaDeTrabalhoData));
        return diaDeTrabalho;
    }
    
    /**
     * Importa registros do rep para a aplicação
     */
    private void importarRegistros() {
      List<Registros> registrosREP = registrosService.buscarListaDeRegistrosREP();
      registrosService.importarRegistros(registrosREP);
    }
    
    /**
     * Busca os registros na base e ordena pela hora dos registros
     * @param usuario
     * @return - lista de registros ordenada pela hora
     */
    private List<Registros> montarListaDeRegistrosDoDiaPorUsuario(Usuario usuario){
        List<Registros> listaRegistros = new ArrayList<>();
        List<RegistrosGerais> registrosGerais = registrosRepository.findAll();
        Date hoje = new Date();
        registrosGerais.stream().filter((gerais) -> (gerais.getPis().equals(usuario.getPis()) && gerais.getDataHoraRegistro().getDate() == hoje.getDate()&& gerais.getDataHoraRegistro().getMonth() == hoje.getMonth() && gerais.getDataHoraRegistro().getYear() == hoje.getYear())).forEachOrdered((gerais) -> {
            listaRegistros.add(new Registros(conversor.dataParaStringHora(gerais.getDataHoraRegistro()), gerais.getPis(), gerais.getNSR(), gerais.getDataHoraRegistro(), conversor.dataParaStringHora(gerais.getDataHoraRegistro())));
        });
        Collections.sort(listaRegistros);
        return listaRegistros;
    }
}
