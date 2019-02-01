/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author clovis.rodrigues
 */
@Entity
public class RegistrosGerais {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ID;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraRegistro;
    
    private String pis;
    
    private int NSR;

    public RegistrosGerais(Date dataHoraRegistro, String pis, int NSR) {
        this.dataHoraRegistro = dataHoraRegistro;
        this.pis = pis;
        this.NSR = NSR;
    }

    public RegistrosGerais() {
    }
    
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(Date dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public int getNSR() {
        return NSR;
    }

    public void setNSR(int NSR) {
        this.NSR = NSR;
    }
}
