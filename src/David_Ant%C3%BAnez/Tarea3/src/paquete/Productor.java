/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

import java.util.Random;

/**
 *
 * @author David
 */
public class Productor implements Runnable 
{
    private final Random aleatorio;
    private final Contenedor contenedor;
    private final int idproductor;
    private final int retardo = 1000;
 
  
    public Productor(Contenedor contenedor, int idproductor) 
    {
        this.contenedor = contenedor;
        this.idproductor = idproductor;
        aleatorio = new Random();
    }
 
    @Override
   
    public void run() 
    {
        while(true)
        {
            int poner = aleatorio.nextInt(300);
            contenedor.put(poner);
            System.out.println("El productor " + idproductor + " pone: " + poner);
            try
            {
                Thread.sleep(retardo);
            } 
            catch (InterruptedException e) 
            {
                System.err.println("Productor " + idproductor + ": Error en run -> " + e.getMessage());
            }
        }
    }
    
}
