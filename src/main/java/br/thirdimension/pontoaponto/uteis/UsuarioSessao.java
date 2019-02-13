/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.uteis;

import br.thirdimension.pontoaponto.configuracao.ResourceOwner;
import br.thirdimension.pontoaponto.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author clovis.rodrigues
 */
@Component
public class UsuarioSessao {
    
    /**
     * Obtém o usuário logado
     * @return 
     */
    public Usuario getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ResourceOwner resourceOwner = (ResourceOwner) auth.getPrincipal();
        Usuario usuario = resourceOwner.getUsuario();
        return usuario;
    }
    
    /**
     * Esse método é usado apenas para adicionar o usuário recem cadastrado na
     * sessão do Spring Security para que o usuário não precise se autenticar
     * assim que se cadastra.
     *
     * @param authenticationManager
     * @param usuario
     */
    public void mantemUsuarioAutenticado(AuthenticationManager authenticationManager, Usuario usuario) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new ResourceOwner(usuario), usuario.getCredenciais().getSenha());
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));
    }
    
}
