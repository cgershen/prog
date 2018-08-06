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
    public class MotorFuerza extends Motores{
    private int CabFuerza;

    public MotorFuerza(String Codigo, float precio, int CabFuerza) {
        super(Codigo, precio);
        this.CabFuerza = CabFuerza;
    }

    public int getCabFuerza() {
        return CabFuerza;
    }

    public void setCabFuerza(int CabFuerza) {
        this.CabFuerza = CabFuerza;
    }
}