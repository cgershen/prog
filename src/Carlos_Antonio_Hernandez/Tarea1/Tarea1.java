/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author maestria
 */    
       
    
  ///***Teniendo en cuenta la Tarea 1 si al construir la Función Main en el ciclo FOR que recorre
 ///*** La lista le especificamos que recorra 5 posiciones cuando solo hay 1 elemento nos lanza una  
 ///*** Exception IndexOutOfBoundsException las cual manejamos en este ejemplo
    public class Tarea1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here 
        ArrayList<MotorTrab> list=new ArrayList<MotorTrab>();
        MotorTrab obj1=new MotorTrab("1234", 2000, true);   
        list.add(obj1);
        try {
            
        for(int i=0;i<list.size();i++){
        System.out.println("El código del Motor de Trabajo "+ i+1 +" es: "+list.get(i).getCodigo()+"\n"
                           +"su precio es: "+list.get(i).getPrecio()+"\n"+"y "+cambio(list.get(i).isArtesanal())+" es artesanal");
        }
    }catch(IndexOutOfBoundsException o){
       o.getMessage();
    } 
    }
    public static String cambio(boolean dato){
        if(dato) return "si"; else return "no"; 
    }
}
