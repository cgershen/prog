/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
         
        /*
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }*/
         
        int portNumber = 2002;//Integer.parseInt(args[0]);
         
        System.out.println("Esperando conexion con cliente...");
              
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) 
        {
            System.out.println("Conectado");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.format("Recibi mensaje y lo reenvio: %s%n", inputLine);
                out.println(inputLine);
            }
            
             System.out.println("Fin");
             
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
