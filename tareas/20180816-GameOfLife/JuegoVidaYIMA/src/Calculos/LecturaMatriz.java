package Calculos;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author YIMA
 */
public class LecturaMatriz 
{
    //Método que obtiene la matriz almacena en un archivo de texto
    public static boolean [][] lecturaMatrizGuardada(File archivo)
   {
        BufferedReader entrada;        
        boolean [][] matriz = null;
        
        try
        {
            int filas            = 0;
            entrada              = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(archivo))));            
            String linea         = "";
            Vector<String> lista = new Vector<String>();
            
            while( linea != null )
            { 
                linea = entrada.readLine();
                
                if(linea == null)
                {
                    break;
                }
                
                lista.add(linea);
                filas++;         
            }  
            
            matriz = new boolean[filas][filas];
            
            for(int i=0; i < lista.size(); i++)
            {
                String texto           = lista.get(i);
                StringTokenizer tokens = new StringTokenizer(texto);
                
                int j=0; 
                
                while(tokens.hasMoreTokens())
                {
                    String token      = tokens.nextToken();                    
                    boolean ubicacion = token.equals("1") ? true : false;                    
                    matriz[i][j]      = ubicacion;
                    j++;
                }
            }
        }
        
        catch(Exception e){}
        
       return matriz;
   }
}
