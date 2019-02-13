/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.time.LocalTime;

/**
 *
 * @author clovis.rodrigues
 */
public class RegistroDiaDto implements Comparable<RegistroDiaDto> {

    private Long id;

    private LocalTime hora;

    private String horaFormatada;

    public RegistroDiaDto() {
    }

    public RegistroDiaDto(Long id, LocalTime hora, String horaFormatada) {
        this.id = id;
        this.hora = hora;
        this.horaFormatada = horaFormatada;
    }

    public RegistroDiaDto(LocalTime hora, String horaFormatada) {
        this.hora = hora;
        this.horaFormatada = horaFormatada;
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

    public String getHoraFormatada() {
        return horaFormatada;
    }

    public void setHoraFormatada(String dataHoraFormatada) {
        this.horaFormatada = dataHoraFormatada;
    }

    @Override
    public int compareTo(RegistroDiaDto o) {
        return getHora().compareTo(o.getHora());
    }

    @Override
    public String toString() {
        return "'{" + '"' + "id" + '"' + ":" + id + ", "
                + '"' + "hora" + '"' + ":" + '"' + hora + '"' + ", "
                + '"' + "horaFormatada" + '"' + ":" + '"' + horaFormatada + '"'
                + "}'";
    }
}
