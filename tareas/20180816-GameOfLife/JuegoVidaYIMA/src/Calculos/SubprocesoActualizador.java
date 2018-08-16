
package Calculos;

import Vistas.Lienzo;

/**
 *
 * @author YIMA
 */
public class SubprocesoActualizador implements Runnable
{
    public volatile boolean _detener;
    private Lienzo _lienzo;
    public volatile int descanzo;
    
    //Constructor de la clase
    public SubprocesoActualizador(Lienzo _lienzo)
    {
        this._lienzo = _lienzo;
        _detener     = false;
        descanzo     = 400;
    }
    
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                while(_detener)
                {
                    try
                    {   
                        
                        Thread.sleep(descanzo);
                        _lienzo.recalcularUbicaciones();
                    }
                    catch(Exception error)
                    {
                    }
                    
                }
            }
        }
        catch(Exception error)
        {            
        }
    }
}
