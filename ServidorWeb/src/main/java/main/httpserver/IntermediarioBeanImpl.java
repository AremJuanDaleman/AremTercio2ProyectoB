/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.httpserver;

import main.httpserver.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2107990
 */

@Service
public class IntermediarioBeanImpl implements IntermediarioBean{
    
    @Autowired
    private Api api;
    
    public IntermediarioBeanImpl(){

    }
    
    @Override
    public String getRespuesta(String num) {
        return api.getResult(num);
    }
    
    
}
