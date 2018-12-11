/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author clovis.rodrigues
 */
public class RegistrosDto implements Comparable<RegistrosDto>{

    private Long id;

    private String dataRegistroFormatada;
    
    private LocalDate dataRegistro;
    
    private List<RegistroDiaDto> hora;
    

    public RegistrosDto(Long id, String dataRegistroFormatada, LocalDate dataRegistro, List<RegistroDiaDto> hora) {
        this.id = id;
        this.dataRegistroFormatada = dataRegistroFormatada;
        this.dataRegistro = dataRegistro;
        this.hora = hora;
    }

    public RegistrosDto(List<RegistroDiaDto> hora) {
        this.dataRegistroFormatada = "";
        this.hora = hora;
    }
    

    public RegistrosDto() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getDataRegistroFormatada() {
        return dataRegistroFormatada;
    }

    public void setDataRegistroFormatada(String dataHoraRegistro) {
        this.dataRegistroFormatada = dataHoraRegistro;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    
    public List<RegistroDiaDto> getHora() {
        return hora;
    }

    public void setHora(List<RegistroDiaDto> hora) {
        this.hora = hora;
    }

    @Override
    public int compareTo(RegistrosDto o) {
        return getDataRegistro().compareTo(o.getDataRegistro());
    }

    @Override
    public String toString() {
        return "'{" + '"' + "id" + '"' + ":" + id + ", "
                    + '"' + "dataRegistroFormatada" + '"' + ":" + '"' + dataRegistroFormatada + '"' + ", "
                    + '"' + "dataRegistro" + '"' + ":" + '"' + dataRegistro + '"' + ", "
                    + '"' + "hora" + '"' + ":" + '"' + hora + '"'
                    + "}'";
    }
}
