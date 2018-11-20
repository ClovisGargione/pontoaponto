/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

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
}
