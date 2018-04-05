/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.httpserver.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2108419
 */
public class Cubo implements Api {        
    
    @Override
    public String getResult(String num){
        URL restApi = null;
        String res = "";
        try {
            restApi = new URL("https://proyectoarem2.herokuapp.com/"+num);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cuadrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(restApi.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                res += inputLine;
            }
        }catch (IOException x) {
            System.err.println(x);
        }                
        
        int numi = Integer.parseInt(res);                
        int rese = numi+numi;
        String resultado = String.valueOf(rese);
        
        return resultado;
    }
    
}