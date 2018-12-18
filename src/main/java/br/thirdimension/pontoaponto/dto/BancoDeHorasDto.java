/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.time.LocalTime;

/**
 *
 * @author clovis.rodrigues
 */
public class BancoDeHorasDto {
    
    private LocalTime total;
    
    private LocalTime negativas;
    
    private LocalTime extras;
    
    private String totalFormatado;
    
    private String negativasFormatado;
    
    private String extrasFormatado;

    public BancoDeHorasDto(LocalTime total, LocalTime negativas, LocalTime extras) {
        this.total = total;
        this.negativas = negativas;
        this.extras = extras;
    }

    public BancoDeHorasDto(String totalFormatado, String negativasFormatado, String extrasFormatado) {
        this.totalFormatado = totalFormatado;
        this.negativasFormatado = negativasFormatado;
        this.extrasFormatado = extrasFormatado;
    }

    public BancoDeHorasDto(LocalTime total, LocalTime negativas, LocalTime extras, String totalFormatado, String negativasFormatado, String extrasFormatado) {
        this.total = total;
        this.negativas = negativas;
        this.extras = extras;
        this.totalFormatado = totalFormatado;
        this.negativasFormatado = negativasFormatado;
        this.extrasFormatado = extrasFormatado;
    }

    public BancoDeHorasDto() {
    }

    public LocalTime getTotal() {
        return total;
    }

    public void setTotal(LocalTime total) {
        this.total = total;
    }

    public LocalTime getNegativas() {
        return negativas;
    }

    public void setNegativas(LocalTime negativas) {
        this.negativas = negativas;
    }

    public LocalTime getExtras() {
        return extras;
    }

    public void setExtras(LocalTime extras) {
        this.extras = extras;
    }

    public String getTotalFormatado() {
        return totalFormatado;
    }

    public void setTotalFormatado(String totalFormatado) {
        this.totalFormatado = totalFormatado;
    }

    public String getNegativasFormatado() {
        return negativasFormatado;
    }

    public void setNegativasFormatado(String negativasFormatado) {
        this.negativasFormatado = negativasFormatado;
    }

    public String getExtrasFormatado() {
        return extrasFormatado;
    }

    public void setExtrasFormatado(String extrasFormatado) {
        this.extrasFormatado = extrasFormatado;
    }
}
