package Vistas;

import Eventos.ClickEvent;
import Eventos.ClickEventListener;
import Eventos.TimeEvent;
import Eventos.TimeEventListener;
import com.sun.prism.paint.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author YIMA
 */
public class VistaControles extends JPanel implements ActionListener, ChangeListener  
{
    private ClickEventListener receptor;
    private TimeEventListener receptorTiempo;
    private JButton inicio;
    private JButton fin;
    private JSlider deslizador;    
    private JButton guardar;
    private JButton importar;
    
    //Constructor de la clase
    public VistaControles()
    {
        setLayout(new GridLayout(1,7,50,50));
        
        inicio      = new JButton("--INICIAR--");
        fin         = new JButton("--DETENER--");      
        guardar     = new JButton("--GUARDAR--");
        importar    = new JButton("--IMPORTAR--");
        deslizador  = new JSlider(JSlider.HORIZONTAL,0,10,10);
        
        TitledBorder border = new TitledBorder("Configuración.");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);
        
        deslizador.addChangeListener(this);
        deslizador.setMajorTickSpacing(1);
        deslizador.setMinorTickSpacing(1);
        deslizador.setPaintTicks(true);
        deslizador.setPaintLabels(true);
        
        fin.setBorderPainted(false);
        fin.setContentAreaFilled(false);
        
        guardar.setBorderPainted(false);
        guardar.setContentAreaFilled(false);
        
        importar.setBorderPainted(false);
        importar.setContentAreaFilled(false);
        
        inicio.setBorderPainted(false);
        inicio.setContentAreaFilled(false);
        
        
        guardar.addActionListener(this);
        importar.addActionListener(this);
        inicio.addActionListener(this);
        fin.addActionListener(this);
        
        this.add(deslizador);
        this.add(inicio);
        this.add(fin);
        this.add(guardar);
        this.add(importar);
    }
    
    //Método que atiene el evento del slider, y a su vez lanza el evento al panel principal lienzo
    public void stateChanged(ChangeEvent e)
    {
        JSlider objeto = (JSlider)e.getSource();        
        int tiempo     = (int)objeto.getValue();
        tiempo         *= 40;
        
        receptorTiempo.cambioTiempo(new TimeEvent(this, tiempo));
    }
    
    //Método que suscribe los eventos
    public void agregarEvento(ClickEventListener receptor, TimeEventListener receptorTiempo)
    {
        this.receptor       = receptor;
        this.receptorTiempo = receptorTiempo;
    }
    
    
    //Método que atiene los eventos de los botones del panel
    @Override
    public void actionPerformed(ActionEvent e) 
    {        
      if(e.getSource() == fin)
      {
          receptor.atencionEvento(new ClickEvent(this, false));
      }
      else if (e.getSource() == inicio)
      {
          receptor.atencionEvento(new ClickEvent(this, true));
      }
      else if(e.getSource() == importar)
      {
          receptor.atencionEvento(new ClickEvent(this, false));
          receptor.importarArchivo(new ClickEvent(this,""));
      }
      else if(e.getSource() == guardar)
      {
          receptor.atencionEvento(new ClickEvent(this, false));
          
          String nombreArchivo = JOptionPane.showInputDialog(this,"escriba el nombre del archivo", "Almacenar información", JOptionPane.WARNING_MESSAGE);
          
          if(nombreArchivo != null && !nombreArchivo.equals(""))
          {
            receptor.guardarArchivo(new ClickEvent(this,nombreArchivo));
            JOptionPane.showMessageDialog(this,"Archivo guardado.","Guardado",JOptionPane.WARNING_MESSAGE);
          }
      }
    }
}
