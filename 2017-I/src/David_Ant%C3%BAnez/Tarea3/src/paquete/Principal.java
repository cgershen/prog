package paquete;


import paquete.Contenedor;


/**
 *
 * @author David
 */
public class Principal 
{
    private static Contenedor contenedor;
    private static Thread productor;
    private static Thread [] consumidores;
    private static final int cantidad_consumidores = 5;
     
  
    public static void main(String[] args) 
    {
        contenedor = new Contenedor();
        productor = new Thread(new Productor(contenedor, 1));
        consumidores = new Thread[cantidad_consumidores];
 
        for(int i = 0; i < cantidad_consumidores; i++)
        {
            consumidores[i] = new Thread(new Consumidor(contenedor, i));
            consumidores[i].start();
        }
         
        productor.start();
    }    
}
