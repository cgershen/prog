
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        String marca, modelo, transmision, tipo;
        int cilindros = 0, potencia = 0, control = 0;

        marca = JOptionPane.showInputDialog("Teclea la marca del automovil");

        modelo = JOptionPane.showInputDialog("Teclea el modelo del automovil");

        transmision = JOptionPane.showInputDialog("Teclea la transmision del automovil");

        do {

            try {
                control = 0;
                cilindros = Integer.parseInt(JOptionPane.showInputDialog("Teclea los cilindros del automovil"));
            } catch (Exception e) {
                control = 1;
                JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres 0-9");
            }

        } while (control == 1);

        do {
            try {
                control = 0;
                potencia = Integer.parseInt(JOptionPane.showInputDialog("Teclea la potencia del automovil"));
            } catch (Exception e) {
                control = 1;
                JOptionPane.showMessageDialog(null, "Solo se aceptan caracteres 0-9");
            }
        } while (control == 1);
        
        

        tipo = JOptionPane.showInputDialog("Teclea el tipo del motor del automovil");

        Modelo modelo1 = new Modelo(marca, modelo, cilindros, transmision, tipo, potencia);

        modelo1.setId_Modelo(1);

        modelo1.imprimir();
    }

}
