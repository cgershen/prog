
import javax.swing.JOptionPane;


public class Modelo extends Automovil
{
    public int id_modelo;
    public String modelo;
    
    public Modelo(String Marca, String Tipo, int Cilindros, String Transmision, String Motor, int Potencia)
    {
        super(Marca, Tipo, Cilindros, Transmision, Motor, Potencia);
        
    }
    
    public void setId_Modelo(int id_modelo)
    {
        this.id_modelo = id_modelo;
    }
    
    public int getId_Modelo()
    {
        return id_modelo;
    }
    
    public void imprimir()
    {
        JOptionPane.showMessageDialog(null,"Marca: " + this.marca + "\n\nTipo: " + this.tipo + "\n\nCilindros: " + this.cilindros + "\n\nTransmisión: " + this.transmision + "\n\nMotor: " + this.motor + "\n\nPotencia: " + this.potencia);
    }
    
}
