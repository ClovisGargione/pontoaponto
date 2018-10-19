/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author clovis.rodrigues
 */
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
    private String nome;
	
    @JsonIgnore
    private Credenciais credenciais;
    
    private String pis;
    
    private String nsr;
	
    public Usuario() {

    }
	
    public Usuario(Integer id, String nome, Credenciais credenciais, String pis, String nsr) {
            super();
            this.id = id;
            this.nome = nome;
            this.credenciais = credenciais;
            this.pis = pis;
            this.nsr = nsr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getNsr() {
        return nsr;
    }

    public void setNsr(String nsr) {
        this.nsr = nsr;
    }
    
    
}
