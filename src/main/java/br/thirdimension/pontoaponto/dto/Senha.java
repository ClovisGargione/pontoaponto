/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author clovis
 */
public class Senha {
    
    @NotEmpty(message = "Senha atual é obrigatório")
    private String senhaAntiga;
    
    @NotEmpty(message = "Nova senha é obrigatório")
    private String novaSenha;
    
    @NotEmpty(message = "Repetição de senha é obrigatório")
    private String novaSenhaRepetida;

    public Senha(String senhaAntiga, String novaSenha, String novaSenhaRepetida) {
        this.senhaAntiga = senhaAntiga;
        this.novaSenha = novaSenha;
        this.novaSenhaRepetida = novaSenhaRepetida;
    }

    public Senha() {
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenhaRepetida() {
        return novaSenhaRepetida;
    }

    public void setNovaSenhaRepetida(String novaSenhaRepetida) {
        this.novaSenhaRepetida = novaSenhaRepetida;
    }
    
    
    
}
