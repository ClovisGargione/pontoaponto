/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.exception.PesquisarException;
import br.thirdimension.pontoaponto.model.Registros;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author clovis.rodrigues
 */
@Repository
public class RegistrosCustomRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public int buscarUltimoNSR() throws Exception {
        TypedQuery<Integer> query = em.createQuery("select max(r.NSR) from RegistrosGerais r", Integer.class);
        try {
                int ultimoNSR = query.getSingleResult();
                return ultimoNSR;
        }catch(NoResultException e) {
                throw new Exception(e.getMessage());
        }
    }
    
    public List<Registros> listarRegistroEntreDatasPorUsuario(Date dataInicial, Date dataFinal, String pis) throws PesquisarException{
         TypedQuery<Registros> query = em.createQuery("SELECT r FROM RegistrosGerais r WHERE r.pis = :pis AND r.dataHoraRegistro BETWEEN :dataInicial AND :dataFinal", Registros.class);
         query.setParameter("pis", pis);
         query.setParameter("dataInicial", dataInicial);
         query.setParameter("dataFinal", dataFinal);
         List<Registros> listaDeRegistros = new ArrayList<>();
         try{
              listaDeRegistros = query.getResultList();
         }catch(Exception ex){
             Logger.getLogger(RegistrosCustomRepository.class.getName()).log(Level.SEVERE, null, ex);
             throw new PesquisarException("Não foi possível localizar os registros entre as datas!");
         }
         return listaDeRegistros;
    }
}
