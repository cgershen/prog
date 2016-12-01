package paquete;


public class  Contenedor extends Thread 
{
    private int contenido;
    private boolean contenedorlleno;
 
  
    public synchronized int get()
    {
        while (!contenedorlleno)
        {
            try
            {
                wait();
            } 
            catch (InterruptedException e) 
            {
                System.err.println("Contenedor: Error en get -> " + e.getMessage());
            }
        }
        contenedorlleno = false;
        notify();
        return contenido;
    }
 
    
    public synchronized void put(int value) 
    {
        while (contenedorlleno) 
        {
            try
            {
                wait();
            } 
            catch (InterruptedException e) 
            {
                System.err.println("Contenedor: Error en put -> " + e.getMessage());
            }
        }
        contenido = value;
        contenedorlleno = true;
        notify();
    }
    
}
