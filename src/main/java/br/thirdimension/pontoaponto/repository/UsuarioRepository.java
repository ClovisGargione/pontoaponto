/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author clovis.rodrigues
 */
@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
    
}
