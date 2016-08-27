import java.io.*;

public class EjemplosPOO{

   public static void main(String args[]){
      ArrayList al = new ArrayList();
      al.add("U");
      al.add("N");
      al.add("A");
      al.add("M");

      System.out.print("Contenido original de al: ");
      Iterator itr = al.iterator();
      while(itr.hasNext()) { // Si existe un elemento siguiente, se ejecuta
         Object elemento = itr.next();
         System.out.print(elemento + " ");
      }

      try{
         int a[] = new int[2];
         System.out.println("Acceder al 3er elemento :" + a[3]);
      }catch(ArrayIndexOutOfBoundsException e){
         System.out.println("Excepción lanzada:" + e);
      }finally{
         a[0] = 6;
         System.out.println("Valor del primer elemento: " +a[0]);
         System.out.println("La instrucción finally se ha ejecutado");
      }
      System.out.println("Fuera del rango");

      try{	
		 file = new FileInputStream(fileName);
		 x = (byte) file.read();
	  }catch (IOException|FileNotFoundException ex) {
         logger.log(ex);
         throw ex;
	  }
   }
}
