/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 *
 * @author clovis.rodrigues
 */
@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

    @Configuration
    public static class ConfiguracaoParaUsuario extends WebSecurityConfigurerAdapter {

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String[] caminhosPermitidos = new String[]{"/", "/home", "/usuarios", "/webjars/**", "/static/**",
                "/jquery*", "/api/helloworld"};

            // @formatter:off
            http.authorizeRequests().antMatchers(caminhosPermitidos).permitAll().anyRequest().authenticated().and()
                    .formLogin().permitAll().loginPage("/login").and().logout().permitAll().and().csrf().disable();
            // @formatter:on
        }

        @Bean
        public static NoOpPasswordEncoder passwordEncoder() {
            return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
        }
    }

}
