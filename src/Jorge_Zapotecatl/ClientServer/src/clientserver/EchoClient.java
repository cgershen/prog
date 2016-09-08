/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.io.*;
import java.net.*;
 
public class EchoClient {
    public static void main(String[] args) throws IOException {
         
        /*if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }*/
 
        String hostName = "Jorge-PC";//args[0];
        int portNumber = 2002;//Integer.parseInt(args[1]);
 
        System.out.println("Abriendo socket con el servidor...");
        try (Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) 
        {
            String userInput;
            
            System.out.println("Conectado con Servidor, escribe un mensaje:");
            while ((userInput = stdIn.readLine()) != null) {
                System.out.format("Enviar mensaje: %s%n", userInput);
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
            
             System.out.println("Fin");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}

