/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.controller;

import br.thirdimension.pontoaponto.dto.CredenciaisDoUsuarioDto;
import br.thirdimension.pontoaponto.dto.SenhaDto;
import br.thirdimension.pontoaponto.model.Credenciais;
import br.thirdimension.pontoaponto.model.Registros;
import br.thirdimension.pontoaponto.model.Usuario;
import br.thirdimension.pontoaponto.repository.UsuarioRepository;
import br.thirdimension.pontoaponto.uteis.Conversores;
import br.thirdimension.pontoaponto.uteis.UsuarioSessao;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    
    @Autowired
    private UsuarioSessao sessao;
    
    @Autowired
    private Conversores conversores;

    /**
     * Tela de cadastro
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("usuarios/formulario");
        CredenciaisDoUsuarioDto credenciaisDoUsuario = new CredenciaisDoUsuarioDto();
        mv.addObject("cabecalho", "Cadastrar usuário");
        mv.addObject("titulo", "Criar um novo usuário");
        mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
        return mv;
    }

    /**
     * Método que recebe os dados do formulário de cadastro de usuário
     *
     * @param credenciaisDoUsuario
     * @param bindingResult
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registrar(@Valid CredenciaisDoUsuarioDto credenciaisDoUsuario, BindingResult bindingResult) {
        ModelAndView mv = null;
        Usuario usuario = null;
        List<Registros> registros = new ArrayList<>();
        if(!credenciaisDoUsuario.getSenha().equals(credenciaisDoUsuario.getConfirmacaoSenha())){
            ObjectError error = new FieldError("credenciaisDoUsuario", "confirmacaoSenha", "Senha e confirmação de senha devem ser iguais");
            
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) {
            mv = new ModelAndView("usuarios/formulario");
            mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
            return mv;
        }
        LocalTime jornadaDeTrabalho = LocalTime.of(credenciaisDoUsuario.getHoras(), credenciaisDoUsuario.getMinutos());
        Integer saldoInicialBancoDeHoras = conversores.converterHoraParaMinutos(credenciaisDoUsuario.getSaldoHoras() != null ? credenciaisDoUsuario.getSaldoHoras() : 0, credenciaisDoUsuario.getSaldoMinutos() != null ? credenciaisDoUsuario.getSaldoMinutos() : 0);
        if(!credenciaisDoUsuario.isSaldoPositivo()){
            saldoInicialBancoDeHoras = -saldoInicialBancoDeHoras;
        }
        if(credenciaisDoUsuario.getId() != null){
            Optional<Usuario> user = usuarioRepository.findById(credenciaisDoUsuario.getId());
            registros = user.get().getRegistros();
        }
        
        //cria um usuario no sistema
        usuario = new Usuario(credenciaisDoUsuario.getId(), credenciaisDoUsuario.getNome(), new Credenciais(credenciaisDoUsuario.getEmail(), credenciaisDoUsuario.getSenha()), credenciaisDoUsuario.getPis(), jornadaDeTrabalho, registros, saldoInicialBancoDeHoras);

        // persiste os dados do usuario
        usuarioRepository.save(usuario);

        // autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
        sessao.mantemUsuarioAutenticado(authenticationManager, usuario);

        // usuário cadastrado é redirecionado para página de controle de livros
        mv = new ModelAndView("redirect:/home");

        return mv;
    }

    @RequestMapping(path = "/editar", method = RequestMethod.GET)
    public ModelAndView editar() {
        ModelAndView mv = new ModelAndView("usuarios/formulario");
        Usuario usuario = sessao.getUsuario();
        int horas = usuario.getJornadaDeTrabalho().getHour();
        int minutos = usuario.getJornadaDeTrabalho().getMinute();
        Integer saldoInicialBanco = usuario.getBancoDeHorasInicial() != null ? usuario.getBancoDeHorasInicial() : 0;
        boolean ehPositiva = saldoInicialBanco > 0 ? true : false;
        int saldoInicial[] = new int[2];
        saldoInicial = conversores.minutosParaHoraEminutos(saldoInicialBanco > 0 ? saldoInicialBanco : Math.abs(-saldoInicialBanco));
        CredenciaisDoUsuarioDto credenciaisDoUsuario = new CredenciaisDoUsuarioDto(usuario.getId(), usuario.getNome(), usuario.getCredenciais().getEmail(), usuario.getCredenciais().getSenha(), usuario.getCredenciais().getSenha(), usuario.getPis(), horas, minutos, saldoInicial[0], saldoInicial[1], ehPositiva);
        mv.addObject("cabecalho", "Atualizar cadastro");
        mv.addObject("titulo", "Editar dados do perfil");
        mv.addObject("credenciaisDoUsuario", credenciaisDoUsuario);
        return mv;
    }
    
    @RequestMapping(path="/redefinirsenha", method = RequestMethod.GET)
    public ModelAndView redefinirSenha() {
        ModelAndView mv = new ModelAndView("usuarios/senha");
        SenhaDto senha = new SenhaDto();
        senha.setSenhaAntiga(sessao.getUsuario().getCredenciais().getSenha());
        mv.addObject("senha", senha);
        return mv;
    }
    
    @RequestMapping(path="/redefinirsenha", method = RequestMethod.POST)
    public ModelAndView salvarSenha(@Valid SenhaDto senha, BindingResult bindingResult) {
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
        
        Usuario usuario = sessao.getUsuario();
        usuario.getCredenciais().setSenha(senha.getNovaSenha());
        usuarioRepository.save(usuario);
        // autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
        sessao.mantemUsuarioAutenticado(authenticationManager, usuario);
        mv = new ModelAndView("redirect:/home");
        return mv;
    }

}
