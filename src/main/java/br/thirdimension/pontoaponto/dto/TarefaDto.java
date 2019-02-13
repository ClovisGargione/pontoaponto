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
public class TarefaDto {
    
    private Long ID;
    
    private String descricao;
    
    private LocalTime tempo;
    
    private String tempoFormatado;
    
    private long registroId;

    public TarefaDto(String descricao, LocalTime tempo) {
        this.descricao = descricao;
        this.tempo = tempo;
    }

    public TarefaDto(Long ID, String descricao, LocalTime tempo, String tempoFormatado, long registroId) {
        this.ID = ID;
        this.descricao = descricao;
        this.tempo = tempo;
        this.tempoFormatado = tempoFormatado;
        this.registroId = registroId;
    }

    public TarefaDto() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }

    public String getTempoFormatado() {
        return tempoFormatado;
    }

    public void setTempoFormatado(String tempoFormatado) {
        this.tempoFormatado = tempoFormatado;
    }

    public long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(long registroId) {
        this.registroId = registroId;
    }
    
    @Override
    public String toString() {
        return "'{" + '"' + "id" + '"' + ":" + ID + ", "
                    + '"' + "descricao" + '"' + ":" + '"' + descricao + '"' + ", "
                    + '"' + "tempo" + '"' + ":" + '"' + tempo + '"' + ", "
                    + '"' + "registroId" + '"' + ":" + '"' + registroId + '"'
                    + "}'";
    }
}
