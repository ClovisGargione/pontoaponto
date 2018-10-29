/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author clovis.rodrigues
 */
public class CredenciaisDoUsuario {

    private Integer id;
    
    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @NotEmpty(message = "Login é obrigatório")
    private String email;

    @NotEmpty(message = "Senha é obrigatório")
    private String senha;
    
    @NotEmpty(message = "Confirmação da senha é obrigatório")
    private String confirmacaoSenha;
    
    @NotEmpty(message = "PIS é obrigatório")
    private String pis;

    public CredenciaisDoUsuario() {
        super();
    }

    public CredenciaisDoUsuario(Integer id, @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha, @NotEmpty String confirmacaoSenha, @NotEmpty String pis) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmacaoSenha = confirmacaoSenha;
        this.pis = pis;
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

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }
    

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }
}
