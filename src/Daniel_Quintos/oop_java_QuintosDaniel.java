interface Persona{
	public static final String descripcion = "Humano";
	
	public abstract void saludar();
	public abstract void trabajar();
	public abstract void comer();
	
}

class Estudiante implements Persona{
	
	private String nombre;
	
	public Estudiante(String nombre){
		this.nombre = nombre;
	}
	
	public void saludar(){
		System.out.println(descripcion+" "+nombre+": Soy un estudiante saludando");
	}
	
	public void trabajar(){
		System.out.println(descripcion+" "+nombre+": Estudiando...");
	}
	
	public void comer(){
		System.out.println(descripcion+" "+nombre+": Comiendo como estudihambre...");
	}
	
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
}

class Maestro implements Persona{
	
	protected String nombre;
	
	public Maestro(String nombre){
		this.nombre = nombre;
	}
	
	public void saludar(){
		System.out.println(descripcion+" "+nombre+": Soy un maestro saludando");
	}
	
	public void trabajar(){
		System.out.println(descripcion+" "+nombre+": Enseñando...");
	}
	
	public void comer(){
		System.out.println(descripcion+" "+nombre+": Comiendo a otro nivel...");
	}
	
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
}

class Asesor extends Maestro{
	
	public Asesor(String nombre){
		super(nombre);
	}
	
	public void saludar(){
		System.out.println(descripcion+" "+nombre+": Soy un maestro-asesor saludando");
	}
	
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
}

public class oop_java_QuintosDaniel{
	
	public static void main(String[] args){
		
		Estudiante e1 = new Estudiante("Daniel");
		e1.saludar(); e1.trabajar(); e1.comer();
		
		Maestro m1 = new Maestro("Carlos");
		m1.saludar(); m1.trabajar(); m1.comer();
		
		Asesor a1 = new Asesor("X");
		a1.saludar(); a1.trabajar(); a1.comer();
		
		Persona p1 = new Estudiante("Rosa");
		p1.saludar(); p1.trabajar(); p1.comer();
		
		Persona p2 = new Asesor("Y");
		p2.saludar(); p2.trabajar(); p2.comer();
		
	}
	
}