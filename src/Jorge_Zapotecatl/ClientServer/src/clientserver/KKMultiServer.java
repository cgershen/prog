/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserver;

import java.net.*;
import java.io.*;

//Threads///////////////////////////////////////////////////////////////////////

class KKMultiServerThread extends Thread {
  private Socket clientSocket;
  
  KKMultiServerThread(Socket clientSocket){  
     this.clientSocket = clientSocket;  
  }  
      public void run() {
      try (    
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      ){
        String inputLine, outputLine;
        // Initiate conversation with client
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        outputLine = kkp.processInput(null);
        out.println(outputLine);
 
        while ((inputLine = in.readLine()) != null) {
            outputLine = kkp.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Bye."))
            break;
        }
      }catch (IOException e) {
        System.out.println(e.getMessage());
      }
        //System.out.println("Hello from a thread (extends Thread)!");
    }
}

class KKMultiServer {
    public static void main(String[] args) throws IOException {
        /*  if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1); }*/
        int portNumber = 2002;//Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(portNumber);){
            int i = 0;
            while (true){
              System.out.format("Cliente %d%n", i);
              try {
                Socket clientSocket = serverSocket.accept();
                KKMultiServerThread threadServer = new KKMultiServerThread(clientSocket);  
                threadServer.start(); 
              }
              catch(IOException e){
                     System.out.println(e.getMessage()); 
              }
              i++;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}