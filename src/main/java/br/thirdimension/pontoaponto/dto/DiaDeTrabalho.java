/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

/**
 *
 * @author clovis.rodrigues
 */
public class DiaDeTrabalho {
    
    private String tempoTralhado;
    
    private String tempoFaltanteExtra;
    
    private String horaSaida;
    
    private boolean extra;
    
    private int tempoTrabalhadoEmMinutos;
    
    private int tempoFaltanteExtraEmMinutos;

    public DiaDeTrabalho() {
    }

    public DiaDeTrabalho(String tempoTralhado, String tempoFaltanteExtra, String horaSaida, boolean extra, int tempoTrabalhadoEmMinutos, int tempoFaltanteExtraEmMinutos) {
        this.tempoTralhado = tempoTralhado;
        this.tempoFaltanteExtra = tempoFaltanteExtra;
        this.horaSaida = horaSaida;
        this.extra = extra;
        this.tempoTrabalhadoEmMinutos = tempoTrabalhadoEmMinutos;
        this.tempoFaltanteExtraEmMinutos = tempoFaltanteExtraEmMinutos;
    }
    
    public String getTempoTralhado() {
        return tempoTralhado;
    }

    public void setTempoTralhado(String tempoTralhado) {
        this.tempoTralhado = tempoTralhado;
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
    
    
    
}
