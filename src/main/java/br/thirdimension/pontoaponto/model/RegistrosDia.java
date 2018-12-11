/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author clovis.rodrigues
 */
@Entity
public class RegistrosDia {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private LocalTime hora;
    
    @ManyToOne
    @JoinColumn(name = "registros_id", nullable = true)
    private Registros registros;

    public RegistrosDia() {
    }

    public RegistrosDia(Long id, LocalTime hora) {
        this.id = id;
        this.hora = hora;
    }

    public RegistrosDia(LocalTime hora, Registros registro) {
        this.hora = hora;
        this.registros = registro;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Registros getRegistros() {
        return registros;
    }

    public void setRegistros(Registros registros) {
        this.registros = registros;
    }
    
    

}
