package Calculos;

import estructurasInformacion.Buscar;
import estructurasInformacion.Puntos;
import java.util.Vector;

/**
 *
 * @author YIMA
 */
public class CalcularNuevasVidas 
{    
    //Matriz con las ubicaciones nuevas calculadas
    private boolean matrizNueva [][];
    
    //Lista de puntos que se dibujan
    private Vector<Puntos> casillas;
    
    //Vector de comparacion tiempo t-1
    private Vector<Puntos> anterior;
    
    //Constructor de la clase
    public CalcularNuevasVidas()
    {
        casillas = null;    
        anterior = new Vector<Puntos>();
    }
    
    //Método que calcula las nuevas celulas con sus ubicaciones
    public boolean [][] calcularVidas(boolean [][] matrizUbicaciones, int iMinimo, int iMaximo, int jMinimo, int jMaximo)
    {
        //Se inicializa la matriz que seré el resultado
        inicializarMatrizResultado(matrizUbicaciones.length);
        
        //Se crean las nuevas vidas
        crearDestruyeVidas(matrizUbicaciones);        
        
        return matrizNueva;
    }
    
    //Método que calcula las nueva vidas
    public Vector<Puntos> calcularVidas(boolean [][] matrizUbicaciones,Vector<Puntos> antes)
    {
        //Limpia el vector anteior y calcula el vector nuevo
        limpiarVectorAnterior(antes);
        
        //Se inicializa la matriz que seré el resultado
        inicializarMatrizResultado(matrizUbicaciones.length);
        
        //Se crean las nuevas vidas
        crearDestruyeVidas(matrizUbicaciones);        
        
        return casillas;
    }
    
    //Método que inicializa la matriz nueva de resultados
    public void inicializarMatrizResultado(int nuevoTamanio)
    {
        matrizNueva = new boolean[nuevoTamanio][nuevoTamanio];
        casillas    = new Vector<Puntos>();
    }
    
    //Método que permite limpiar el vector de puntos anteriores,, y establece los nuevos valores
    public void limpiarVectorAnterior(Vector<Puntos> antes)
    {
        anterior.clear();
        
        for(int i = 0; i< antes.size(); i++)
        {
            anterior.add(antes.get(i));
        }
    }
    
    public Vector<Puntos> obtenerComparacion(Vector<Puntos> tMenosUno)
    {
        Vector<Puntos> inter = new Vector<Puntos>();
        
        for(int i = 0; i< anterior.size(); i++)
        {
            Puntos puntoAnterior = anterior.get(i);
            
            for(int j = 0; j < tMenosUno.size(); j++)
            {
                Puntos actual = tMenosUno.get(j);
                
                if(actual.i == puntoAnterior.i &&
                   actual.j == puntoAnterior.j)
                {
                    inter.add(actual);
                    break;
                }
            }
        }
        return inter;
    }
    
    /* Método que tiene como objetivo crear las nuevas celulas, de acuerdo a la regla de solo si en la casilla se tiene
       unicamente 3 vecinos
    */
    public void crearDestruyeVidas(boolean [][] matrizUbicaciones)
    {
        int vecinosVivos = 0;
        int iLimite      = 0;
        int jLimite      = 0;
        
        for(int i=0; i < matrizUbicaciones.length; i++)
        {
            for(int j=0; j < matrizUbicaciones.length; j++)
            {
                if( (i-1) >= 0)
                {
                    iLimite = (i-1);
                }
                else
                {
                    iLimite = 0;                        
                }

                if((j-1) >= 0)
                {
                    jLimite = j-1;
                }
                else
                {
                    jLimite = 0;
                }
                    
                vecinosVivos = contarVecinosPorEstado(matrizUbicaciones,iLimite, jLimite, Buscar.VIVO);
                    
                if(!matrizUbicaciones[i][j])//Si la ubicacion está vacio procedo a intentar colocar mi nueva célula
                {
                    if(vecinosVivos == 3)
                    {
                        matrizNueva[i][j] = true;
                        casillas.add(crearPunto(i,j));
                    }
                }
                else
                {
                    vecinosVivos --;
                    
                    if(vecinosVivos == 2 || vecinosVivos == 3)
                    {
                        matrizNueva[i][j] = true; //Si la celula unicamente esta rodeada de 2 0 3 vecinos puede vivir                        
                        casillas.add(crearPunto(i,j));
                    }
                }        
            }
        }
    }    
    
    //Método que crea un objeto del tipo Puntos
    public Puntos crearPunto(int i, int j)
    {
        Puntos punto = new Puntos();
        punto.i      = i;
        punto.j      = j;
    
        return punto;
    }
    
    //método que busca los vecinos de una celula, ya sea que estan vivos o muertos
    public int contarVecinosPorEstado(boolean [][] matrizUbicaciones, int iInicial, int jInicial, Buscar tipoBusqueda)
    {
        int acumulador = 0;
        
        for(int i=iInicial; i < matrizUbicaciones.length && (i != (iInicial + 3) ); i++)
        {
            for(int j=jInicial; j < matrizUbicaciones.length && (j != (jInicial + 3) ); j++)
            {
                if(tipoBusqueda == Buscar.VIVO )
                {
                    if(matrizUbicaciones[i][j])
                    {
                        acumulador ++;
                    }
                }
            }
        }
        
        return acumulador;
    }
}
