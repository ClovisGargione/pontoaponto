/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

/**
 *
 * @author clovis.rodrigues
 */
public class Registros {

    private String valor;

    public Registros(String valor) {

        this.valor = valor;
    }

    public Registros() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
