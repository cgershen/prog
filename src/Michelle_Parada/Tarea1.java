//Tarea 1) Programación Avanzada - PCIC-UNAM

 public class Persona {

    private String nombre;
    private String apellidos;
    private int edad;

    public Persona (String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;                   
    }

    public String getNombre () { 
        return nombre;  
    }

    public String getApellidos () {
        return apellidos;
    }

    public int getEdad () {
        return edad;   
    }

} 

public class Vendedor extends Persona {

    private String IdVendedor;

    //Agregamos como parámetros los del constructor de la superclase Persona
    public Vendedor (String nombre, String apellidos, int edad) {

        super(nombre, apellidos, edad);
        IdVendedor = "Desconocido";  
    }

    public void setIdVendedor (String IdVendedor) { 
        this.IdVendedor = IdVendedor;   
    }

    public String getIdVendedor () { 
        return IdVendedor;   
    }

    //Accedemos a los métodos de la superclase Persona
    public void mostrarDatos() {
        System.out.println ("Vendedor: " + getNombre() + " " +  getApellidos() +
         ", Identificador: " + getIdVendedor() ); }

}

 public class EjemploHerencia {

    public static void main (String [ ] Args) {

        Vendedor vend1 = new Vendedor ("Juliette Michelle", "Parada Carvallo", 25);
        vend1.setIdVendedor("PCIC-UNAM");
        vend1.mostrarDatos();}

}
