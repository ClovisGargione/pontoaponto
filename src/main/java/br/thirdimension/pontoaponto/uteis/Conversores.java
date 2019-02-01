/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.uteis;

import br.thirdimension.pontoaponto.controller.RegistrosController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis.rodrigues
 */
@Component
public class Conversores {
    
    private static final int HORA = 0;
    private static final int MINUTO = 1;
    
    public int[] minutosParaHoraEminutos(int tempo){
        int[] hora = new int[2];
        hora[HORA] = (int) (tempo / 60);
        hora[MINUTO] = (int) (tempo % 60); 
        hora = ajustarMinutos(hora[HORA], hora[MINUTO]);
        return hora;
    }
    
    public int[] ajustarMinutos(int horas, int minutos){
        int[] horaAjustada = new int[2];
        if (minutos > 59) {
            horaAjustada = minutosParaHoraEminutos(minutos);
            horas += horaAjustada[HORA];
            minutos = horaAjustada[MINUTO];
        }
        horaAjustada[HORA] = horas;
        horaAjustada[MINUTO] = minutos;
        return horaAjustada;
    }
    
    public int converterHoraParaMinutos(int hora, int minutos){
        return ((hora * 60) + minutos);
    }
    
    public String dataParaStringHora(Date data) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dataString = format.format(data);
        return dataString;
    }
    
    public String dataParaString(Date data) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = format.format(data);
        return dataString;
    }
    
    public String localTimeParaStringHora(LocalTime hora) {
        String dataString = hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return dataString;
    }
    
    public String localDateParaString(LocalDate data) {
        String dataString = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return dataString;
    }
    
    public Date stringParaData(String dataString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = null;
        try {
            data = new Date(format.parse(dataString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(RegistrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public Date definirHoraMinutoEmData(int hora, int minuto) {
        Date data = new Date();
        data.setMinutes(minuto);
        data.setHours(hora);
        return data;
    }
    
}
