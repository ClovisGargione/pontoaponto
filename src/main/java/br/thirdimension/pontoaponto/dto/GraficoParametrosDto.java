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
public class GraficoParametrosDto {
    
    private List<String> labels;
    
    private List<String>  datasetsLabel;
    
    private List<Integer> datasetsData;
    
    private List<String> dataBackgroundColor;
    
    private List<String> dataBorderColor;

    public GraficoParametrosDto() {
        labels = new ArrayList<>();
        datasetsLabel = new ArrayList<>();
        datasetsData = new ArrayList<>();
        dataBackgroundColor = new ArrayList<>();
        dataBorderColor = new ArrayList<>();
    }

    public GraficoParametrosDto(List<String> labels, List<String> datasetsLabel, List<Integer> datasetsData, List<String> dataBackgroundColor, List<String> dataBorderColor) {
        this.labels = labels;
        this.datasetsLabel = datasetsLabel;
        this.datasetsData = datasetsData;
        this.dataBackgroundColor = dataBackgroundColor;
        this.dataBorderColor = dataBorderColor;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getDatasetsLabel() {
        return datasetsLabel;
    }

    public void setDatasetsLabel(List<String> datasetsLabel) {
        this.datasetsLabel = datasetsLabel;
    }

    public List<Integer> getDatasetsData() {
        return datasetsData;
    }

    public void setDatasetsData(List<Integer> datasetsData) {
        this.datasetsData = datasetsData;
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
