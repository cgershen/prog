//hilo de los corredores
import javax.swing.*;

public class hiloCarreras extends Thread {
	JProgressBar corredor;
	JTextArea mensajes;
	JLabel nombre;
	JButton boton;
	static int FIN = 100;
	
	public hiloCarreras(String str, JProgressBar jpb, JTextArea jta, JLabel jl,JButton jb) {
		super(str);
		this.corredor = jpb;
		this.mensajes = jta;
		this.nombre = jl;
		this.boton = jb;
		this.corredor.setMinimum(0);
		this.corredor.setMaximum(FIN);		
		this.nombre.setText(str);
	}	
	
	public void run(){
		for (int i = 0; i < 101; i++) {
			corredor.setValue(i);
			try {
				sleep((int) (Math.random() * 400));
			} catch (InterruptedException e) {
				mensajes.append(String.valueOf(e)+"\n");
			}
		}
		mensajes.append("Finaliza: " + getName() + "\n");
		
		SwingUtilities.invokeLater ( new Runnable ()
                        {
                            @Override
                            public void run ()
                            {
                                boton.setEnabled ( true );
                            }
                        } );

	}	
	
}
