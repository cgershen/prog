class Palabrejas implements Runnable{
	String palabra;
	
	public Palabrejas(String plb){
		palabra = plb;
	}
	
	public void run(){
		System.out.print(palabra + " ");
	}

}

