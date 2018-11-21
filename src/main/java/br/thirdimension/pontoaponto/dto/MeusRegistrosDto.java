/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author clovis
 */
public class MeusRegistrosDto {
    
    private String dataHoraFormatada;
    
    private List<String> horaDoRegistro;

    public MeusRegistrosDto() {
        horaDoRegistro = new ArrayList<>();
    }

    public MeusRegistrosDto(String dataHoraFormatada, List<String> horaDoRegistro) {
        this.dataHoraFormatada = dataHoraFormatada;
        this.horaDoRegistro = horaDoRegistro;
    }

    public String getDataHoraFormatada() {
        return dataHoraFormatada;
    }

    public void setDataHoraFormatada(String dataHoraFormatada) {
        this.dataHoraFormatada = dataHoraFormatada;
    }

    public List<String> getHoraDoRegistro() {
        return horaDoRegistro;
    }

    public void setHoraDoRegistro(List<String> horaDoRegistro) {
        this.horaDoRegistro = horaDoRegistro;
    }
    
}
