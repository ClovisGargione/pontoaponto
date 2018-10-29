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
    
    private String PIS;
    
    private String NSR;

    public RegistrosGerais(Date dataHoraRegistro, String PIS, String NSR) {
        this.dataHoraRegistro = dataHoraRegistro;
        this.PIS = PIS;
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

    public String getPIS() {
        return PIS;
    }

    public void setPIS(String PIS) {
        this.PIS = PIS;
    }

    public String getNSR() {
        return NSR;
    }

    public void setNSR(String NSR) {
        this.NSR = NSR;
    }
    
    
    
}
