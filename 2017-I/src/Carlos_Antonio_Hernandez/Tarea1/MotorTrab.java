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
public class MotorTrab extends Motores {
    private boolean Artesanal;

    public MotorTrab(String Codigo, float precio, boolean Artesanal) {
        super(Codigo, precio);
        this.Artesanal = Artesanal;
    }

    public boolean isArtesanal() {
        return Artesanal;
    }

    public void setArtesanal(boolean Artesanal) {
        this.Artesanal = Artesanal;
    }
}