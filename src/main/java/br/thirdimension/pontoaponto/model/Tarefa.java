/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author clovis.rodrigues
 */
@Entity
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ID;
    
    private String descricao;
    
    private LocalTime tempo;
    
    @ManyToOne
    @JoinColumn(name = "registros_id", nullable = false)
    private Registros registros;

    public Tarefa() {
    }

    public Tarefa(String descricao, LocalTime tempo, Registros registros) {
        this.descricao = descricao;
        this.tempo = tempo;
        this.registros = registros;
    }

    public Tarefa(String descricao, LocalTime tempo) {
        this.descricao = descricao;
        this.tempo = tempo;
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

    public Registros getRegistros() {
        return registros;
    }

    public void setRegistros(Registros registros) {
        this.registros = registros;
    }
}
