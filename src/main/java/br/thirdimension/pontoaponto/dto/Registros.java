/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.util.Date;

/**
 *
 * @author clovis.rodrigues
 */
public class Registros implements Comparable<Registros>{


    private String dataHoraRegistro;
    
    private String pis;
    
    private int nsr;
    
    private Date dataHora;
    
    private String dataHoraFormatada;

    public Registros(String dataHoraRegistro, String PIS, int NSR, Date dataHora, String dataHoraFormatada) {
        this.dataHoraRegistro = dataHoraRegistro;
        this.pis = PIS;
        this.nsr = NSR;
        this.dataHora = dataHora;
        this.dataHoraFormatada = dataHoraFormatada;
    }

    public Registros(String PIS, Date dataHora) {
        this.dataHoraRegistro = "";
        this.pis = PIS;
        this.nsr = 0;
        this.dataHora = dataHora;
        this.dataHoraFormatada = "";
    }
    

    public Registros() {
        
    }

    public String getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(String dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String PIS) {
        this.pis = PIS;
    }

    public int getNsr() {
        return nsr;
    }

    public void setNsr(int NSR) {
        this.nsr = NSR;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getDataHoraFormatada() {
        return dataHoraFormatada;
    }

    public void setDataHoraFormatada(String dataHoraFormatada) {
        this.dataHoraFormatada = dataHoraFormatada;
    }

    @Override
    public int compareTo(Registros o) {
        return getDataHora().compareTo(o.getDataHora());
    }

    
    
}
