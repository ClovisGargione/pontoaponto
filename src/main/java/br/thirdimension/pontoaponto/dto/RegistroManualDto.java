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
public class RegistroManualDto {
    
    private long registroId;
    
    private String registroDia;

    public RegistroManualDto(long registroId, String registroDia) {
        this.registroId = registroId;
        this.registroDia = registroDia;
    }

    public RegistroManualDto() {
    }

    public long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(long registroId) {
        this.registroId = registroId;
    }

    public String getRegistroDia() {
        return registroDia;
    }

    public void setRegistroDia(String registroDia) {
        this.registroDia = registroDia;
    }
}
