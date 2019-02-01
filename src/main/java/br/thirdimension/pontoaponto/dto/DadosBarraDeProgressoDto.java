/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author clovis
 */
public class DadosBarraDeProgressoDto {
    
    private int  horasTrabalhadas;
    
    private int faltante;
    
    private int extra;

    public DadosBarraDeProgressoDto() {
    }

    public DadosBarraDeProgressoDto(int horasTrabalhadas, int faltante, int extra) {
        this.horasTrabalhadas = horasTrabalhadas;
        this.faltante = faltante;
        this.extra = extra;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public int getFaltante() {
        return faltante;
    }

    public void setFaltante(int faltante) {
        this.faltante = faltante;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
    
    public String getTempoTrabalhadoString(){
        LocalTime localTime = LocalTime.MIN;
        localTime = localTime.plusMinutes(horasTrabalhadas);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String trabalhadas = "<b>Trab.:</b> " + "<span style='color: #91c5f2'><b>"+localTime.format(dtf)+"</b></span>";
        if(extra > 0){
            localTime = LocalTime.MIN;
            localTime = localTime.plusMinutes(extra);
            trabalhadas += " <b>Extras:</b> " + "<span style='color: #73dd95'><b>" + localTime.format(dtf) + "</b></span>"; 
        }if(faltante > 0){
            localTime = LocalTime.MIN;
            localTime = localTime.plusMinutes(faltante);
            trabalhadas += " <b>Negativas:</b> " + "<span style='color: #ffdb85'><b>" + localTime.format(dtf) + "</b></span>"; 
        }
        return trabalhadas;
    }
}
