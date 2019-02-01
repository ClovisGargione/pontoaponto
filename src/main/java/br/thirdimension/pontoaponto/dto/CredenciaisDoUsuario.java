/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author clovis.rodrigues
 */
public class CredenciaisDoUsuario {

    private Long id;
    
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
    
    @NotNull(message = "Jornada de trabalho é obrigatório")
    @Min(value = 1, message = "Horas devem ser superior a um")
    @Max(value = 23, message = "Horas devem ser inferior a vinte e vinte e três")
    private int horas;
    
    @NotNull(message = "Jornada de trabalho é obrigatório")
    @Min(value = 0, message = "Minutos devem ser superior a zero")
    @Max(value = 59, message = "Minutos devem ser inferior a cinquenta e nove")
    private int minutos;

    public CredenciaisDoUsuario() {
        super();
    }

    public CredenciaisDoUsuario(Long id, @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha, @NotEmpty String confirmacaoSenha, @NotEmpty String pis, @NotEmpty int horas, @NotEmpty int minutos) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmacaoSenha = confirmacaoSenha;
        this.pis = pis;
        this.horas = horas;
        this.minutos = minutos;
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

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    
}
