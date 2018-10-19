/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author clovis.rodrigues
 */
public class CredenciaisDoUsuario {

    private Integer id;
    
    @NotEmpty
    private String nome;

    @NotEmpty
    private String email;

    @NotEmpty
    private String senha;
    
    @NotEmpty
    private String pis;
    
    @NotEmpty(message = "NSR é obrigatório")
    private String nsr;

    public CredenciaisDoUsuario() {
        super();
    }

    public CredenciaisDoUsuario(Integer id, @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha, @NotEmpty String pis, @NotEmpty String nsr) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
