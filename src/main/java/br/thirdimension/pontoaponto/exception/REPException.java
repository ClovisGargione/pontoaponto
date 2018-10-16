/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.thirdimension.pontoaponto.exception;

/**
 *
 * @author clovis.rodrigues
 */
public class REPException  extends Exception{
    
    private static final long serialVersionUID = 1L;
	
	public REPException(String mensagem) {
        super(mensagem);
    }
    
}
