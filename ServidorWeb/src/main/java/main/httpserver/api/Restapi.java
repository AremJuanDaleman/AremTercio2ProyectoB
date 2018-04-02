/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.httpserver.api;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
/**
 *
 * @author 2107990
 */
@Service
public class Restapi implements Api {        
    
    @Override
    public String getResult(String num){
        URL restApi = null;
        String res = "";
        try {
            restApi = new URL("https://proyectoarem2.herokuapp.com/"+num);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Restapi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(restApi.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                res += inputLine;
            }
        }catch (IOException x) {
            System.err.println(x);
        }
        
        return res;
    }
    
}
