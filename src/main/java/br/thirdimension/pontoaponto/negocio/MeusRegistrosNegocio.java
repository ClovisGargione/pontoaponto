/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.negocio;

import br.thirdimension.pontoaponto.dto.MeusRegistrosDto;
import br.thirdimension.pontoaponto.model.RegistrosGerais;
import br.thirdimension.pontoaponto.service.RegistrosService;
import br.thirdimension.pontoaponto.uteis.Conversores;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis
 */
@Component
public class MeusRegistrosNegocio {

    @Autowired
    private Conversores conversores;

    @Autowired
    private RegistrosService registrosService;

    public List<MeusRegistrosDto> buscarListaDeRegistrosAgrupadaPorData() {
        List<MeusRegistrosDto> meusRegistros = new ArrayList<>();
        List<RegistrosGerais> registros = registrosService.buscarListaDeRegistrosDoUsuario();
        meusRegistros = agruparRegistros(registros);
        return meusRegistros;
    }

    private List<MeusRegistrosDto> agruparRegistros(List<RegistrosGerais> registros) {
        List<MeusRegistrosDto> meusRegistros = new ArrayList<>();
        List<MeusRegistrosDto> meusRegistros_ = new ArrayList<>();
        MeusRegistrosDto meuRegistro = new MeusRegistrosDto();
        List<String> horaRegistro = new ArrayList<>();
        for (RegistrosGerais registro : registros) {
            if (meuRegistro.getDataHoraFormatada() == null || !meuRegistro.getDataHoraFormatada().equals(conversores.dataParaString(registro.getDataHoraRegistro()))) {
                meuRegistro.setDataHoraFormatada(conversores.dataParaString(registro.getDataHoraRegistro()));
                meusRegistros.add(new MeusRegistrosDto(meuRegistro.getDataHoraFormatada(), new ArrayList<>()));
            }
        }
        for (MeusRegistrosDto m : meusRegistros) {
            for (RegistrosGerais registro_ : registros) {
                if (m.getDataHoraFormatada().equals(conversores.dataParaString(registro_.getDataHoraRegistro()))) {
                    horaRegistro.add(conversores.dataParaStringHora(registro_.getDataHoraRegistro()));
                }
            }
            m.getHoraDoRegistro().addAll(horaRegistro);
            meusRegistros_.add(m);
            horaRegistro.clear();
        }
        return meusRegistros_;
    }

}
