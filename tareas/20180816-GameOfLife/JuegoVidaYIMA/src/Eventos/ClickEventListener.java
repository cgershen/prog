package Eventos;

import java.util.EventListener;

/**
 *
 * @author YIMA
 */
public interface ClickEventListener extends EventListener
{
     public abstract void atencionEvento(ClickEvent e);
     public abstract void guardarArchivo(ClickEvent e);
     public abstract void importarArchivo(ClickEvent e);
}
