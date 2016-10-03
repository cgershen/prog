/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author maestria
 */
public class Tarea2 {
      
    
    public Tarea2() {
    }
 
    
    public ArrayList<Double> listar(ArrayList<Double> lista){
        
        ArrayList<Double> calculo = new ArrayList<Double>(); 
        Iterator <Double> lista1= lista.iterator();
        while(lista1.hasNext()){
        calculo.add(lista1.next());
        }        
        return calculo;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }  
}
