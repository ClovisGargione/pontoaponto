/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.Usuario;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author clovis.rodrigues
 */
@Repository
public interface RegistrosRepository extends PagingAndSortingRepository<Registros, Long>{
    
    @Query("SELECT r FROM Registros r WHERE r.usuario = ?1")
    Page<Registros> findByUsuario(Usuario usuario, Pageable pageable);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro = ?1 AND r.usuario = ?2")
    Registros buscarUltimoRegistroInserido(LocalDate dataAtual, Usuario usuario);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro BETWEEN ?1 AND ?2 AND r.usuario = ?3")
    Page<Registros> listarRegistroEntreDatasPorUsuario(LocalDate dataInicial, LocalDate dataFinal, Usuario usuario, Pageable pageable);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro > ?1 AND r.usuario = ?2")
    Page<Registros> listarRegistrosApartirDaData(LocalDate dataInicial, Usuario usuario, Pageable pageable);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro < ?1 AND r.usuario = ?2")
    Page<Registros> listarRegistrosAteAData(LocalDate dataFinal, Usuario usuario, Pageable pageable);  
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro BETWEEN ?1 AND ?2 AND r.usuario = ?3 AND MOD((select count(d) from RegistrosDia d where d.registros = r),2) != 0")
    Page<Registros> listarRegistrosIncompletosEntreDatasPorUsuario(LocalDate dataInicial, LocalDate dataFinal, Usuario usuario, Pageable pageable);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro > ?1 AND r.usuario = ?2 AND MOD((select count(d) from RegistrosDia d where d.registros = r),2) != 0")
    Page<Registros> listarRegistrosIncompletosApartirDaData(LocalDate dataInicial, Usuario usuario, Pageable pageable);
    
    @Query("SELECT r FROM Registros r WHERE r.dataRegistro < ?1 AND r.usuario = ?2 AND MOD((select count(d) from RegistrosDia d where d.registros = r),2) != 0")
    Page<Registros> listarRegistrosIncompletosAteAData(LocalDate dataFinal, Usuario usuario, Pageable pageable);  
    
    @Query("SELECT r FROM Registros r WHERE r.usuario = ?1 and MOD((select count(d) from RegistrosDia d where d.registros = r),2) != 0")
    Page<Registros> listarRegistrosIncompletos(Usuario usuario, Pageable pageable);
    
    
}
