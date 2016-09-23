/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

/**
 *
 * @author David
 */
public class Consumidor implements Runnable
{
    private final Contenedor contenedor;
    private final int idconsumidor;
 
 
    public Consumidor(Contenedor contenedor, int idconsumidor) 
    {
        this.contenedor = contenedor;
        this.idconsumidor = idconsumidor;
    }
 
    @Override
    
    public void run() 
    {
        while(true)
        {
            System.out.println("El consumidor " + idconsumidor + " consume: " + contenedor.get());
        }
    }
    
}
