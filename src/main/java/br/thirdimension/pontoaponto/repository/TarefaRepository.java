/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.model.Tarefa;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author clovis.rodrigues
 */
public interface TarefaRepository extends PagingAndSortingRepository<Tarefa, Long>{
    
}
