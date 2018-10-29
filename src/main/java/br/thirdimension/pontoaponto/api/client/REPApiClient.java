/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.api.client;

import br.thirdimension.pontoaponto.dto.Registros;
import br.thirdimension.pontoaponto.exception.REPException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author clovis.rodrigues
 */
@Service
public class REPApiClient {

    private static final String ENDPOINT = "http://localhost:3000/api/rep";

    public List<Registros> registros() throws REPException {
        RestTemplate resttemplate = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        //Verificar com @fabio se irá precisar de cabecalho
        //headers.add("Authorization", "Bearer " + token);

        RequestEntity<Registros> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ENDPOINT));

        try {
            ResponseEntity<Registros[]> resposta = resttemplate.exchange(request, Registros[].class);
            if (resposta.getStatusCode().is2xxSuccessful()) {
                return listaFromArray(resposta.getBody());
            } else {
                throw new RuntimeException("sem sucesso");
            }
        } catch (HttpClientErrorException e) {
            throw new REPException("não foi possível obter os registros de ponto do REP!");
        }
    }

    private List<Registros> listaFromArray(Registros[] registros) {
        List<Registros> lista = new ArrayList<>();
        SimpleDateFormat format = null;
        for (Registros registro : registros) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
               Date data = new Date(format.parse(registro.getDataHoraRegistro()).getTime());
                registro.setDataHora(data);
            } catch (ParseException ex) {
                Logger.getLogger(REPApiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            registro.setDataHoraFormatada(format.format(registro.getDataHora()));
            lista.add(registro);
        }

        return lista;
    }
    
    @Deprecated
    public static void main(String[] args ){
        
        
        REPApiClient apiClient = new REPApiClient();
        try {
            List<Registros> registroses = apiClient.registros();
            System.out.println(registroses.isEmpty());
        } catch (REPException ex) {
            Logger.getLogger(REPApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
