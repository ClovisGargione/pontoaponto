/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import org.springframework.stereotype.Component;

/**
 *
 * @author clovis.rodrigues
 */
@Component
public class DiaDeTrabalho {
    
    private String tempoTrabalhado;
    
    private String tempoFaltanteExtra;
    
    private String horaSaida;
    
    private boolean extra;
    
    private int tempoTrabalhadoEmMinutos;
    
    private int tempoFaltanteExtraEmMinutos;
    
    private String labelExtrasNegativas;
    
    private String jornadaDeTrabalho;

    public DiaDeTrabalho() {
    }

    public DiaDeTrabalho(String tempoTralhado, String tempoFaltanteExtra, String horaSaida, boolean extra, int tempoTrabalhadoEmMinutos, int tempoFaltanteExtraEmMinutos, String labelExtrasNegativas, String jornadaDeTrabalho) {
        this.tempoTrabalhado = tempoTralhado;
        this.tempoFaltanteExtra = tempoFaltanteExtra;
        this.horaSaida = horaSaida;
        this.extra = extra;
        this.tempoTrabalhadoEmMinutos = tempoTrabalhadoEmMinutos;
        this.tempoFaltanteExtraEmMinutos = tempoFaltanteExtraEmMinutos;
        this.labelExtrasNegativas = labelExtrasNegativas;
        this.jornadaDeTrabalho = jornadaDeTrabalho;
    }
    
    public String getTempoTrabalhado() {
        return tempoTrabalhado;
    }

    public void setTempoTrabalhado(String tempoTralhado) {
        this.tempoTrabalhado = tempoTralhado;
    }

    public String getTempoFaltanteExtra() {
        return tempoFaltanteExtra;
    }

    public void setTempoFaltanteExtra(String tempoFaltanteExtra) {
        this.tempoFaltanteExtra = tempoFaltanteExtra;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public int getTempoTrabalhadoEmMinutos() {
        return tempoTrabalhadoEmMinutos;
    }

    public void setTempoTrabalhadoEmMinutos(int tempoTrabalhadoEmMinutos) {
        this.tempoTrabalhadoEmMinutos = tempoTrabalhadoEmMinutos;
    }

    public int getTempoFaltanteExtraEmMinutos() {
        return tempoFaltanteExtraEmMinutos;
    }

    public void setTempoFaltanteExtraEmMinutos(int tempoFaltanteExtraEmMinutos) {
        this.tempoFaltanteExtraEmMinutos = tempoFaltanteExtraEmMinutos;
    }

    public String getLabelExtrasNegativas() {
        return labelExtrasNegativas;
    }

    public void setLabelExtrasNegativas(String labelExtrasNegativas) {
        this.labelExtrasNegativas = labelExtrasNegativas;
    }

    public String getJornadaDeTrabalho() {
        return jornadaDeTrabalho;
    }

    public void setJornadaDeTrabalho(String jornadaDeTrabalho) {
        this.jornadaDeTrabalho = jornadaDeTrabalho;
    }
    
}
