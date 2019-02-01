/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.time.LocalTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis.rodrigues
 */
@Component
public class DiaDeTrabalhoDto {
    
    private String tempoTrabalhadoFormatado;
    
    private String tempoFaltanteExtraFormatado;
    
    private String horaSaidaFormatada;
    
    private boolean extra;
    
    private LocalTime tempoTrabalhado;
    
    private LocalTime tempoFaltanteExtra;
    
    private String labelExtrasNegativas;
    
    private String jornadaDeTrabalho;

    public DiaDeTrabalhoDto() {
    }

    public DiaDeTrabalhoDto(String tempoTralhado, String tempoFaltanteExtraFormatado, String horaSaidaFormatada, boolean extra, LocalTime tempoTrabalhado, LocalTime tempoFaltanteExtra, String labelExtrasNegativas, String jornadaDeTrabalho) {
        this.tempoTrabalhadoFormatado = tempoTralhado;
        this.tempoFaltanteExtraFormatado = tempoFaltanteExtraFormatado;
        this.horaSaidaFormatada = horaSaidaFormatada;
        this.extra = extra;
        this.tempoTrabalhado = tempoTrabalhado;
        this.tempoFaltanteExtra = tempoFaltanteExtra;
        this.labelExtrasNegativas = labelExtrasNegativas;
        this.jornadaDeTrabalho = jornadaDeTrabalho;
    }
    
    public String getTempoTrabalhadoFormatado() {
        return tempoTrabalhadoFormatado;
    }

    public void setTempoTrabalhadoFormatado(String tempoTralhado) {
        this.tempoTrabalhadoFormatado = tempoTralhado;
    }

    public String getTempoFaltanteExtraFormatado() {
        return tempoFaltanteExtraFormatado;
    }

    public void setTempoFaltanteExtraFormatado(String tempoFaltanteExtra) {
        this.tempoFaltanteExtraFormatado = tempoFaltanteExtra;
    }

    public String getHoraSaidaFormatada() {
        return horaSaidaFormatada;
    }

    public void setHoraSaidaFormatada(String horaSaida) {
        this.horaSaidaFormatada = horaSaida;
    }

    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public LocalTime getTempoTrabalhado() {
        return tempoTrabalhado;
    }

    public void setTempoTrabalhado(LocalTime tempoTrabalhado) {
        this.tempoTrabalhado = tempoTrabalhado;
    }

    public LocalTime getTempoFaltanteExtra() {
        return tempoFaltanteExtra;
    }

    public void setTempoFaltanteExtra(LocalTime tempoFaltanteExtra) {
        this.tempoFaltanteExtra = tempoFaltanteExtra;
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
