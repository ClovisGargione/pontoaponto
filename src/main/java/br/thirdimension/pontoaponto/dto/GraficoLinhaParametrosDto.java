/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author clovis.rodrigues
 */
public class GraficoLinhaParametrosDto {
    
    private List<String> labels;
    
    private List<String>  datasetsLabelNegativas;
    
    private List<String>  datasetsLabelExtras;
    
    private List<Double> datasetsDataNegativas;
    
    private List<Double> datasetsDataExtras;
    
    private List<String> dataBackgroundColor;
    
    private List<String> dataBorderColor;

    public GraficoLinhaParametrosDto() {
        labels = new ArrayList<>();
        datasetsLabelNegativas = new ArrayList<>();
        datasetsLabelExtras = new ArrayList<>();
        datasetsDataNegativas = new ArrayList<>();
        datasetsDataExtras = new ArrayList<>();
        dataBackgroundColor = new ArrayList<>();
        dataBorderColor = new ArrayList<>();
    }

    public GraficoLinhaParametrosDto(List<String> labels, List<String> datasetsLabelNegativas, List<String> datasetsLabelExtras, List<Double> datasetsDataNegativas, List<Double> datasetsDataExtras,List<String> dataBackgroundColor, List<String> dataBorderColor) {
        this.labels = labels;
        this.datasetsLabelNegativas = datasetsLabelNegativas;
        this.datasetsLabelExtras = datasetsLabelExtras;
        this.datasetsDataNegativas = datasetsDataNegativas;
        this.datasetsDataExtras = datasetsDataExtras;
        this.dataBackgroundColor = dataBackgroundColor;
        this.dataBorderColor = dataBorderColor;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getDatasetsLabelNegativas() {
        return datasetsLabelNegativas;
    }

    public void setDatasetsLabelNegativas(List<String> datasetsLabelNegativas) {
        this.datasetsLabelNegativas = datasetsLabelNegativas;
    }

    public List<String> getDatasetsLabelExtras() {
        return datasetsLabelExtras;
    }

    public void setDatasetsLabelExtras(List<String> datasetsLabelExtras) {
        this.datasetsLabelExtras = datasetsLabelExtras;
    }

    

    public List<Double> getDatasetsDataNegativas() {
        return datasetsDataNegativas;
    }

    public void setDatasetsDataNegativas(List<Double> datasetsDataNegativas) {
        this.datasetsDataNegativas = datasetsDataNegativas;
    }

    public List<Double> getDatasetsDataExtras() {
        return datasetsDataExtras;
    }

    public void setDatasetsDataExtras(List<Double> datasetsDataExtras) {
        this.datasetsDataExtras = datasetsDataExtras;
    }

    

    public List<String> getDataBackgroundColor() {
        return dataBackgroundColor;
    }

    public void setDataBackgroundColor(List<String> dataBackgroundColor) {
        this.dataBackgroundColor = dataBackgroundColor;
    }

    public List<String> getDataBorderColor() {
        return dataBorderColor;
    }

    public void setDataBorderColor(List<String> dataBorderColor) {
        this.dataBorderColor = dataBorderColor;
    }
    
    
}
