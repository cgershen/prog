package Eventos;

import java.util.EventObject;

/**
 *
 * @author YIMA
 */
public class TimeEvent extends EventObject
{
    public int tiempo;
    
    //Constructor de la clase
    public TimeEvent(Object source, int tiempo)
    {
        super(source);
        this.tiempo = tiempo;
    }
}
