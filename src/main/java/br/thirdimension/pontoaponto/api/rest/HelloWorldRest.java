/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author clovis
 */
@RestController
@RequestMapping({"/api/helloworld"})
public class HelloWorldRest {
    
    @GetMapping
    public ResponseEntity<Object> helloWorld(){
        return ResponseEntity.ok("Olá mundo rest!");
        
    }
    
}
