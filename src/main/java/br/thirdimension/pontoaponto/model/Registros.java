/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import java.time.LocalDate;
import java.util.List;
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
public class Registros {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ID;
    
    private LocalDate dataRegistro;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "registros", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<RegistrosDia> registrosDia;

    public Registros(LocalDate dataRegistro, Usuario usuario) {
        this.dataRegistro = dataRegistro;
        this.usuario = usuario;
    }

    public Registros() {
    }
    
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataHoraRegistro) {
        this.dataRegistro = dataHoraRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<RegistrosDia> getRegistrosDia() {
        return registrosDia;
    }

    public void setRegistrosDia(List<RegistrosDia> registroDia) {
        this.registrosDia = registroDia;
    }
}
