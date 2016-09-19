/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

/**
 *
 * @author maestria
 */
public class Hilo extends Thread{

 public Hilo (String str){
   super(str);
 }
 
 public void run (){
    for (int i=0; i<10; i++)  {
     System.out.println((i+1) + " " + getName());
     try  {
     Thread.sleep((int)(Math.random() * 1000));
     } catch(Exception e) {
            }
    }
 }
}
