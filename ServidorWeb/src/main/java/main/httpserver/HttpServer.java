/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.httpserver;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.httpserver.api.Api;

/**
 *
 * @author 
 */

public class HttpServer implements Runnable{
    
    private ServerSocket socket;
    private IntermediarioBean inter;
    

    HttpServer(ServerSocket socket, IntermediarioBean inter) {
        this.socket = socket;
        this.inter = inter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
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
                    File indexFile = new File(HttpServer.class.getResource("/index.html").getFile());
                    String output = null;
                    try {
                        output = FileUtils.readFileToString(indexFile, StandardCharsets.UTF_8);
                    } catch (IOException ex) {
                        Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n\r\n" + output;
                    
                    out.println(outputLine);
                }
                else if(path.contains("solicitud")){
                    
                    System.out.println("BOOOOOOOOOOOOOOOOM");
                   
                    outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n\r\n"                                        
                    + inter.getRespuesta(String.valueOf(path.split("/")[2]));
                    
                    out.println(outputLine);
                    
                    
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
