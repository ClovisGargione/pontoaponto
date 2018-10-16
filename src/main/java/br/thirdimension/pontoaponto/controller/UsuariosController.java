/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.configuracao.ResourceOwner;
import br.thirdimension.pontoaponto.dto.CredenciaisDoUsuario;
import br.thirdimension.pontoaponto.model.Credenciais;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.UsuarioRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author clovis
 */
@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Tela de cadastro
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("usuarios/cadastro");
        CredenciaisDoUsuario dadosDeRegistro = new CredenciaisDoUsuario();
        mv.addObject("dadosDeRegistro", dadosDeRegistro);
        return mv;
    }

    /**
     * Método que recebe os dados do formulário de cadastro de usuário
     *
     * @param dadosDeRegistro
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registrar(@Valid CredenciaisDoUsuario dadosDeRegistro, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("usuarios/cadastro");
        }

        //cria um usuario no sistema
        Usuario usuario = new Usuario(dadosDeRegistro.getNome(), new Credenciais(dadosDeRegistro.getEmail(), dadosDeRegistro.getSenha()), dadosDeRegistro.getPis(), dadosDeRegistro.getNsr());

        // persiste os dados do usuario
        usuarioRepository.save(usuario);

        // autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
        mantemUsuarioAutenticado(authenticationManager, usuario);

        // usuário cadastrado é redirecionado para página de controle de livros
        ModelAndView mv = new ModelAndView("redirect:/home");

        return mv;
    }

    /**
     * Esse método é usado apenas para adicionar o usuário recem cadastrado na
     * sessão do Spring Security para que o usuário não precise se autenticar
     * assim que se cadastra.
     *
     * @param authenticationManager
     * @param usuario
     */
    private void mantemUsuarioAutenticado(AuthenticationManager authenticationManager, Usuario usuario) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new ResourceOwner(usuario), usuario.getCredenciais().getSenha());
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));
    }

}
