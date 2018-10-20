/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.configuracao.ResourceOwner;
import br.thirdimension.pontoaponto.dto.CredenciaisDoUsuario;
import br.thirdimension.pontoaponto.dto.Senha;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
        ModelAndView mv = new ModelAndView("usuarios/formulario");
        CredenciaisDoUsuario credenciaisDoUsuario = new CredenciaisDoUsuario();
        mv.addObject("cabecalho", "Cadastrar usuário");
        mv.addObject("titulo", "Criar um novo usuário");
        mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
        return mv;
    }

    /**
     * Método que recebe os dados do formulário de cadastro de usuário
     *
     * @param dadosDeRegistro
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registrar(@Valid CredenciaisDoUsuario credenciaisDoUsuario, BindingResult bindingResult) {
        ModelAndView mv = null;
        if (bindingResult.hasErrors()) {
            mv = new ModelAndView("usuarios/formulario");
            mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
            return mv;
        }

        //cria um usuario no sistema
        Usuario usuario = new Usuario(credenciaisDoUsuario.getId(), credenciaisDoUsuario.getNome(), new Credenciais(credenciaisDoUsuario.getEmail(), credenciaisDoUsuario.getSenha()), credenciaisDoUsuario.getPis(), credenciaisDoUsuario.getNsr());

        // persiste os dados do usuario
        usuarioRepository.save(usuario);

        // autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
        mantemUsuarioAutenticado(authenticationManager, usuario);

        // usuário cadastrado é redirecionado para página de controle de livros
        mv = new ModelAndView("redirect:/home");

        return mv;
    }

    @RequestMapping(path = "/editar", method = RequestMethod.GET)
    public ModelAndView editar() {
        ModelAndView mv = new ModelAndView("usuarios/formulario");
        Usuario usuario = getUsuarioLogado();
        CredenciaisDoUsuario credenciaisDoUsuario = new CredenciaisDoUsuario(usuario.getId(), usuario.getNome(), usuario.getCredenciais().getEmail(), usuario.getCredenciais().getSenha(), usuario.getPis(), usuario.getNsr());
        mv.addObject("cabecalho", "Atualizar cadastro");
        mv.addObject("titulo", "Editar dados do perfil");
        mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
        return mv;
    }
    
    @RequestMapping(path="/redefinirsenha", method = RequestMethod.GET)
    public ModelAndView redefinirSenha() {
        ModelAndView mv = new ModelAndView("usuarios/senha");
        Senha senha = new Senha();
        senha.setSenhaAntiga(getUsuarioLogado().getCredenciais().getSenha());
        mv.addObject("senha", senha);
        return mv;
    }
    
    @RequestMapping(path="/redefinirsenha", method = RequestMethod.POST)
    public ModelAndView salvarSenha(@Valid Senha senha, BindingResult bindingResult) {
        ModelAndView mv = null;
        if(!senha.getNovaSenha().equals(senha.getNovaSenhaRepetida())){
            ObjectError error = new FieldError("senha", "novaSenhaRepetida", "Nova senha e confirmação de senha devem ser iguais");
            
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) {
            mv = new ModelAndView("usuarios/senha");
            mv.addObject("senha", senha);
            return mv;
        }
        
        Usuario usuario = getUsuarioLogado();
        usuario.getCredenciais().setSenha(senha.getNovaSenha());
        usuarioRepository.save(usuario);
        // autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
        mantemUsuarioAutenticado(authenticationManager, usuario);
        mv = new ModelAndView("redirect:/home");
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

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ResourceOwner resourceOwner = (ResourceOwner) auth.getPrincipal();
        Usuario usuario = resourceOwner.getUsuario();
        return usuario;
    }

}
