/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.model.RegistrosGerais;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author clovis.rodrigues
 */
@Repository
public interface RegistrosRepository extends JpaRepository<RegistrosGerais, Long>{
    
    List<RegistrosGerais> findByPis(String pis);
}
