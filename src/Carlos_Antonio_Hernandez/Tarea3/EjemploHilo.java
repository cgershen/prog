/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

/**
 *
 * @author maestria
 */
public class EjemploHilo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      new Hilo ("Cliente 1").start();
      new Hilo ("Cliente 2").start();
      new Hilo ("Cliente 3").start();
      new Hilo ("Cliente 4").start();
      
    }
    
}
