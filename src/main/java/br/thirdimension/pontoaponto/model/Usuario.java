/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author clovis.rodrigues
 */
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
    private String nome;
	
    @JsonIgnore
    private Credenciais credenciais;
    
    private String pis;
    
    private LocalTime jornadaDeTrabalho;
    
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Registros> registros;
    	
    public Usuario() {

    }
	
    public Usuario(Long id, String nome, Credenciais credenciais, String pis, LocalTime jornadaDeTrabalho, List<Registros> registros) {
            super();
            this.id = id;
            this.nome = nome;
            this.credenciais = credenciais;
            this.pis = pis;
            this.jornadaDeTrabalho = jornadaDeTrabalho;
            this.registros = registros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Credenciais getCredenciais() {
        return credenciais;
    }

    public void setCredenciais(Credenciais credenciais) {
        this.credenciais = credenciais;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }   

    public LocalTime getJornadaDeTrabalho() {
        return jornadaDeTrabalho;
    }

    public void setJornadaDeTrabalho(LocalTime jornadaDeTrabalho) {
        this.jornadaDeTrabalho = jornadaDeTrabalho;
    }

    public List<Registros> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registros> registros) {
        this.registros = registros;
    }
}
