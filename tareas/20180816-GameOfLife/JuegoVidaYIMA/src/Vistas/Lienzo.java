package Vistas;

//Importacion a la paqueteria de clases de configuración
import Calculos.CalcularNuevasVidas;
import Calculos.EscrituraMatriz;
import Calculos.LecturaMatriz;
import Calculos.SubprocesoActualizador;
import Eventos.ClickEvent;
import Eventos.ClickEventListener;
import Eventos.TimeEvent;
import Eventos.TimeEventListener;
import estructurasInformacion.Configuracion;
import estructurasInformacion.Puntos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author YIMA
 */
public class Lienzo extends JPanel implements MouseListener, ClickEventListener, TimeEventListener
{
    //Objeto de configuración de primera instancia
    private Configuracion _configuracion;
    private CalcularNuevasVidas calculador;
    
    //Matriz de ubicaciones
    private boolean matrizUbicacion[][];
    private JLabel matrizVisual [][];
    private Vector<Puntos> puntos;
    
    private boolean ratonPresionado;
    private SubprocesoActualizador hilo;
    
    //Constructor de la clase
    public Lienzo(Configuracion _configuracion)
    {
        this._configuracion     = _configuracion;//Seteo del objeto de configuración
        matrizUbicacion         = new boolean[_configuracion.alto][_configuracion.ancho]; //Se crea la matriz con la configuración por default
        matrizVisual            = new JLabel[_configuracion.alto][_configuracion.ancho];//Se crea la matriz con los objeto visuales
        calculador              = new CalcularNuevasVidas();
        hilo                    = new SubprocesoActualizador(this);
        puntos                  = new Vector<Puntos>(); //Se inicializa el vector de puntos
        ratonPresionado         = false;
        
        //Se establece el color por default en negro
        setBackground(Color.WHITE);
        
        //Se ejecuta el método que dibuja los botones
        dibujarObjetosVisuales(_configuracion.alto);
        
        new Thread(hilo).start();
    }
    
    //Método que dibuja los objetos visuales por primera vez, les pone borde y les agregar eventos
    public void dibujarObjetosVisuales(int configuracion)
    {
        setLayout(new GridLayout(configuracion,configuracion));
        
        for(int i=0; i < configuracion; i++)
        {
            for(int j=0; j < configuracion; j++)
            {                
                matrizVisual[i][j]    = new JLabel(""); //Se inicializa el botón y se no se le pone nombre
                matrizUbicacion[i][j] = false; //Indica que no hay nada en la ubicación
                matrizVisual[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //Borde al label
                matrizVisual[i][j].setBackground(null);                //indica que no tiene color de fondo
                matrizVisual[i][j].setOpaque(true); //Inica que debe de reflejar el color 
                matrizVisual[i][j].addMouseListener(this); //Se agrega la clase de atención de su evento
                add(matrizVisual[i][j]); //se agrega al panel
            }
        }
    }

    //Método que permite ajustar la matriz al tamaño que elija el usuario
    public void aplicarConfiguracionZoom(int valorNuevo)
    {
        for(int i=0; i < _configuracion.ancho; i++)
        {
            for(int j=0; j < _configuracion.ancho; j++)
            {  
                matrizVisual[i][j].removeMouseListener(this);
                this.remove(matrizVisual[i][j]);
            }
        }
        
        hilo._detener        = false;        
        matrizVisual         = new JLabel[valorNuevo][valorNuevo];
        matrizUbicacion      = new boolean[valorNuevo][valorNuevo];
        _configuracion.ancho = valorNuevo;
        _configuracion.alto  = valorNuevo;
        
        puntos.clear();
        dibujarObjetosVisuales(valorNuevo);        
        this.updateUI();
    }
    
    

    //Método que recalcula las nuevas ubicaciones
    public synchronized void recalcularUbicaciones()
    {  
        //matrizUbicacion = calculador.calcularVidas(matrizUbicacion, 0,0,0,0);
        Vector<Puntos> nuevos = calculador.calcularVidas(matrizUbicacion, puntos);
        
        for(int i=0; i < puntos.size(); i++)
        {
            Puntos casillas = puntos.get(i);
            
            matrizVisual[casillas.i][casillas.j].setBackground(null);
            matrizUbicacion[casillas.i][casillas.j] = false;
        }
        
        puntos.clear(); //Se limpian los accesos directos
        
        for(int i=0; i < nuevos.size(); i++)
        {
            Puntos casillas = nuevos.get(i);
            
            matrizVisual[casillas.i][casillas.j].setBackground(Color.BLUE);
            matrizUbicacion[casillas.i][casillas.j] = true;
            puntos.add(casillas);
        }
        
    }
    
    
    //Método que atiende la seleccion del ratón en el label
    @Override
    public void mouseClicked(MouseEvent  e)
    {
        JLabel btn        = (JLabel)e.getSource();        
        boolean encontrado = false;
        
        for(int i=0; i < _configuracion.alto; i++)
        {
            if(encontrado)
            {            
                break;            
            }
            
            for(int j=0; j < _configuracion.ancho; j++)
            {
                if(encontrado)
                {            
                    break;            
                }
                
                if(btn == matrizVisual[i][j])
                {
                    Puntos punto = new Puntos();
                    punto.i = i;
                    punto.j = j;
                    
                    if(matrizUbicacion[i][j])
                    {
                        for(int ii = 0; ii < puntos.size(); ii++)
                        {
                            Puntos uni = puntos.get(ii);
                            if( uni.i == i && uni.j == j)
                            {
                                puntos.remove(ii);
                                break;
                            }
                        }
                        matrizVisual[i][j].setBackground(null); 
                        matrizUbicacion[i][j] = false;
                    }
                    else
                    {                        
                        puntos.add(punto);
                        matrizVisual[i][j].setBackground(Color.BLUE);
                        matrizUbicacion[i][j] = true;
                    }
                    
                    encontrado = true;
                }
            }
        }
    }

    //Método que obtiene la configuración del lienzo
    public Configuracion obtenerConfiguracion()
    {
        return _configuracion;
    }
    
    
    //Método que establece la configracion al lienzo
    public void establecerConfiguracion(Configuracion _configuracion)
    {
        this._configuracion = _configuracion;
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {       
        ratonPresionado = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {     
        ratonPresionado = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        if(ratonPresionado)
        {
            mouseClicked(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {        
    }

    //Método que atención del evento del botón de la vista
    @Override
    public void atencionEvento(ClickEvent e)
    {
        hilo._detener = e.dibujar;
    }

    @Override
    public void cambioTiempo(TimeEvent e) 
    {
        hilo.descanzo = e.tiempo;
    }

    @Override
    public void guardarArchivo(ClickEvent e)
    {
        EscrituraMatriz.almacenarMatrizArchivo(matrizUbicacion, e.nombreArchivo);
    }

    @Override
    public void importarArchivo(ClickEvent e)
    {
        File archivo = obtenerArchivo();
        
        if(archivo != null)
        {            
            matrizUbicacion = LecturaMatriz.lecturaMatrizGuardada(archivo);
            
            for(int i=0; i < _configuracion.ancho; i++)
        {
            for(int j=0; j < _configuracion.ancho; j++)
            {  
                matrizVisual[i][j].removeMouseListener(this);
                this.remove(matrizVisual[i][j]);
            }
        }
            
        matrizVisual         = new JLabel[matrizUbicacion.length][matrizUbicacion.length];        
        _configuracion.ancho = matrizUbicacion.length;
        _configuracion.alto  = matrizUbicacion.length;
        
        puntos.clear();
        setLayout(new GridLayout(matrizUbicacion.length,matrizUbicacion.length));
        for(int i=0; i < matrizUbicacion.length; i++)
        {
            for(int j=0; j < matrizUbicacion.length; j++)
            {  
                matrizVisual[i][j]    = new JLabel(""); //Se inicializa el botón y se no se le pone nombre
                matrizVisual[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //Borde al label
                matrizVisual[i][j].setOpaque(true); //Inica que debe de reflejar el color 
                matrizVisual[i][j].addMouseListener(this); //Se agrega la clase de atención de su evento
                
                if(matrizUbicacion[i][j])
                {
                    matrizVisual[i][j].setBackground(Color.BLUE);                //indica que no tiene color de fondo
                    
                    Puntos punto = new Puntos();
                    punto.i = i;
                    punto.j = j;
                    
                    puntos.add(punto);
                }
                else
                {
                    matrizVisual[i][j].setBackground(null);                //indica que no tiene color de fondo
                }
                
                add(matrizVisual[i][j]); //se agrega al panel
            }
        }
        
        this.updateUI();
        }
    }
    
    public File obtenerArchivo()
    {
        JFileChooser selector =new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int resultado=selector.showOpenDialog(this);
        
        if(resultado == JFileChooser.CANCEL_OPTION)
        {
            return null;
        }
        
        File archivo=selector.getSelectedFile();
        
        if((archivo == null)||(archivo.getName().equals("")))
        {
            JOptionPane.showMessageDialog(this,"Archivo invalido","Error",JOptionPane.ERROR_MESSAGE);
        }
        
        return archivo;
    }


}
