/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.httpserver;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

import main.httpserver.api.*;
import main.httpserver.api.Cuadrado;


/**
 *
 * @author 
 */

public class HttpServer implements Runnable{
    
    private ServerSocket socket;    
    private Api api;
    

    HttpServer(ServerSocket socket) {
        this.socket = socket;        
    }

    @Override
    public void run() {
        while (true) {
            try {
                init();
            } catch (IOException ex) {
                Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private void init() throws IOException{
        Socket clientSocket = null;
        try {            
            clientSocket = socket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        String inputLine, outputLine;
        
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if(inputLine.startsWith("GET")){
                String path = inputLine.split(" ")[1];
                if(path.equals("/") || path.equals("/index.html")){                    
                    //Resource indexFile = new ClassPathXmlApplicationContext("applicationContext.xml").getResource("/index.html");
                    File indexFile =new File("classes/index.html");                    
                    String output="";
                    String text;                    
                    try {                        
                        FileReader input = new FileReader(indexFile);                        
                        BufferedReader br = new BufferedReader(input);
                        
                        while((text = br.readLine())!=null) {
                            output+=text;
                        }
                        /**String text;
                        while ((text = br.readLine()) != null) {
                           output+=text;                        
                        }**/ 
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n\r\n" + output;
                    
                    out.println(outputLine);
                }
                else if(path.contains("solicitud")){
                    
                    System.out.println("BOOOOOOOOOOOOOOOOM");
                    
                    String clase = path.split("/")[2];                    
                    try{
                        Object p = Class.forName("main.httpserver.api."+clase).newInstance();
                        api = (Api) p;      
                        
                        outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n"                                        
                        + api.getResult(String.valueOf(path.split("/")[3]));

                        out.println(outputLine);
                    
                    }catch(Exception e){
                        System.out.println("CLASE NO EXISTENTE");
                    }
                    
                    
                    
                }
            }
            
            if (!in.ready()) {
                break;
            }
            
            if (inputLine.equals("")) break;
        }
        
        out.close();
        in.close();
        clientSocket.close();
    }
    
}
