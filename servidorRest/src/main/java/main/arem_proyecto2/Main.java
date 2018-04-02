/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.arem_proyecto2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author 
 */

@Controller
@SpringBootApplication
public class Main {    
    public static void main(String[] args) throws Exception {    
        SpringApplication.run(Main.class, args);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/{number}")
    public ResponseEntity<?> getSquare(@PathVariable int number){        
        return new ResponseEntity<>(number*number,HttpStatus.OK);
    }    
}
