/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.configuracao;

import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.UsuarioCustomRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author clovis.rodrigues
 */
@Service
public class DadosDoUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioCustomRepository usuarioCustomRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioCustomRepository.buscarPorEmail(email);

        if (usuario.isPresent()) {
            return new ResourceOwner(usuario.get());
        } else {
            throw new UsernameNotFoundException("usuario não autorizado");
        }
    }

}
