/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.api.client;

import org.springframework.stereotype.Service;

/**
 *
 * @author clovis.rodrigues
 */
@Service
public class REPApiClient {

    private static final String ENDPOINT = "http://localhost:3000/api/rep";
/*
    public List<RegistrosDto> registros() throws REPException {
        RestTemplate resttemplate = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        //Verificar com @fabio se irá precisar de cabecalho
        //headers.add("Authorization", "Bearer " + token);

        RequestEntity<RegistrosDto> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ENDPOINT));

        try {
            ResponseEntity<RegistrosDto[]> resposta = resttemplate.exchange(request, RegistrosDto[].class);
            if (resposta.getStatusCode().is2xxSuccessful()) {
                return listaFromArray(resposta.getBody());
            } else {
                throw new RuntimeException("sem sucesso");
            }
        } catch (Exception c) {
            throw new REPException("não foi possível obter os registros de ponto do REP!");
        }
    }*/
/*
    private List<RegistrosDto> listaFromArray(RegistrosDto[] registros) {
        List<RegistrosDto> lista = new ArrayList<>();
        SimpleDateFormat format = null;
        for (RegistrosDto registro : registros) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
               Date data = new Date(format.parse(registro.getDataHoraRegistro()).getTime());
                registro.setDataHora(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException ex) {
                Logger.getLogger(REPApiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            registro.setDataHoraFormatada(format.format(registro.getDataHora()));
            lista.add(registro);
        }

        return lista;
    }*/
    /*
    @Deprecated
    public static void main(String[] args ){
        
        
        REPApiClient apiClient = new REPApiClient();
        try {
            List<RegistrosDto> registroses = apiClient.registros();
            System.out.println(registroses.isEmpty());
        } catch (REPException ex) {
            Logger.getLogger(REPApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

}
