/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

/**
 *
 * @author maestria
 */
public class Motores implements Estados{
    
    private String Codigo;
    private float Precio;

    public Motores(String Codigo, float Precio) {
        this.Codigo = Codigo;
        this.Precio = Precio;
    }

    public String getCodigo() {
        return Codigo;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    @Override
    public void enMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void estacionada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}