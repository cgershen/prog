package estructurasInformacion;

/**
 *
 * @author YIMA
 */
public class Configuracion 
{    
    public int ancho               = 0; //Indica el ancho de la malla
    public int alto                = 0; //Indica el alto de la malla
    public int velocidadRefrescado = 0; //Indica la velocidad de refrescado
    
    //Construtor principal de la clase
    public Configuracion() 
    {
        //Se configuran valores por default
        ancho               = 30;
        alto                = 30;
        velocidadRefrescado = 1;
    }
}
