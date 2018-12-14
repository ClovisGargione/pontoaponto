/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.util.ArrayList;
import java.util.List;

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
}
