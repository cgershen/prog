package Eventos;

import java.util.EventListener;

/**
 *
 * @author YIMA
 */
public interface TimeEventListener extends EventListener
{
    public abstract void cambioTiempo(TimeEvent e);
}
