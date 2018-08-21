
//Importación de la libreria swing de java
import Vistas.Lienzo;
import Vistas.VistaControles;
import estructurasInformacion.Configuracion;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author YIMA
 */
public class VistaJuegoVida extends JFrame implements ChangeListener//Extiendo de JFrame para heredar las caracteristicas de una ventana
{    
    
    private Lienzo mallaPrincipal; //Objeto lienzo
    private Configuracion _configuracion; //Objeto de configuracion principal
    private JMenu menu; //objeto del tipo menu
    private JMenuItem menuSalir; //Submenu
    private VistaControles botonesSuperiores;
    private JSlider zoom;
    
    //Constructor de la clase
    public VistaJuegoVida()
    {
        //Se manda al constructor de la clase JFrame el nombre del titulo de la ventana
        super("Juego de la vida");
        
        //Se manda llamar el método que configura la ventana
        configurarVentanaPrincipal();
        
        //Se manda llamar el método que inserta los componentes en la ventana
        configuracionDeObjetosVisuales();
    }
    
    
    //Método que permite configurar la ventana principal de la aplicación
    public void configurarVentanaPrincipal()
    {        
        //Se elige el administrador de esquemas BorderLayout
        setLayout(new BorderLayout());
        
        //Se establece el tamaño de la ventana
        setSize(1000,1000);
        
        //Se indica que cuando se cierra la aplicación se liberen los recursos
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
        //Se indica que ponga la ventana al frente
        setLocationRelativeTo(null);
        
        //Se mantiene la aplicación siempre visible
        setVisible(true);
    }
    
    //Método que inicia la configuración de los elementos visuales, los inserta y ubica en la posición indicada
    public void configuracionDeObjetosVisuales()
    {
        try
        {
            _configuracion      = new Configuracion(); //Se crea el objeto de configuración
            mallaPrincipal      = new Lienzo(_configuracion); //Se crea el objeto lienzo (donde se dibujaran los elementos)
            botonesSuperiores   = new VistaControles(); 
            
            JMenuBar barraSuperiorMenus = new JMenuBar(); //creación del objeto donde se anidaran los menus
            menu                        = new JMenu("Inicio"); //Creacion del objeto menu
            menuSalir                   = new JMenuItem("Salir"); //Creaqión del submenu salir            
            zoom                        = new JSlider(JSlider.VERTICAL,20,100,30);
            
            
            zoom.setMajorTickSpacing(1);
            zoom.setMinorTickSpacing(1);
            zoom.setPaintTicks(true);
            zoom.setPaintLabels(true);
            botonesSuperiores.agregarEvento(mallaPrincipal, mallaPrincipal);
            
            add(botonesSuperiores, BorderLayout.NORTH);
            add(zoom, BorderLayout.WEST);
            add(mallaPrincipal, BorderLayout.CENTER);  
            
            //Se agrega el submenu al menu padre
            menu.add(menuSalir);
            zoom.addChangeListener(this);
            
            //Se agrega el menua la barra de menus
            barraSuperiorMenus.add(menu);
            
            //Se establece el menu en la parte superior de la ventana
            setJMenuBar(barraSuperiorMenus);
            
            menuSalir.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
           });
        }
        catch(Exception error) //Manejo de cualquier error que ocurra durante la ejecución del método
        {
            JOptionPane.showMessageDialog(null,"Ocurrió un error al cargar los componentes","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Método principal de la clase
    public static void main(String argumentosConsola[])
    {
        //Se incia todo en conjunto
        JFrame ventana = new VistaJuegoVida();
        
        //se ejecuta el método que muestra la ventana
        ventana.show();
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        JSlider objeto = (JSlider)e.getSource();        
        int tiempo     = (int)objeto.getValue();
        mallaPrincipal.aplicarConfiguracionZoom(tiempo);
    }
}
