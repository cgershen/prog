/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author YIMA
 */
public class EscrituraMatriz
{
    //Método que se encarga de almacenar la matriz en un archivo de texto
    public static void almacenarMatrizArchivo(boolean [][] matrizUbicaciones,String nombre)
    {
        try
        {
            File fichero       = new File(nombre+".txt");
            String informacion = "";
            
            fichero.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero,true), "utf8"));
            
            for(int i=0; i< matrizUbicaciones.length; i++)
            {
                for(int j=0; j < matrizUbicaciones.length; j++)
                {
                    informacion = matrizUbicaciones[i][j] ? "1 " : "0 ";
                    bw.write(informacion);		     		    
                }
                bw.newLine();
            }
            
            bw.flush(); 
            bw.close();
        }
        catch(Exception error){}
    }
}
