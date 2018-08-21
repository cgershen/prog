package Eventos;

import java.util.EventObject;

/**
 *
 * @author YIMA
 */
public class ClickEvent extends EventObject 
{
    public boolean dibujar;
    public String nombreArchivo;
    
    //Constructor sobrecargado de la clase
    public ClickEvent(Object source, boolean dibujar)
    {
        super(source);
        this.dibujar = dibujar;
    }
    
    //Constructor sobrecargado de la clase
    public ClickEvent(Object source, String nombreArchivo)
    {
        super(source);
        this.nombreArchivo = nombreArchivo;
    }
    
}
