/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.repository;

import br.thirdimension.pontoaponto.model.Usuario;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author clovis.rodrigues
 */
@Repository
@Transactional
public class UsuarioCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Usuario> buscarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.credenciais.email = :email", Usuario.class);
        query.setParameter("email", email);
        try {
            Usuario usuario = query.getSingleResult();
            return Optional.of(usuario);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
