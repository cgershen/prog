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
    public class MotorAlta extends Motores {
    private int RevolxMin;
    private int Consumo;

    public MotorAlta(String Codigo, float precio, int RevolxMin, int Consumo) {
        super(Codigo, precio);
        this.RevolxMin = RevolxMin;
        this.Consumo = Consumo;
    }

    public int getConsumo() {
        return Consumo;
    }

    public void setConsumo(int Consumo) {
        this.Consumo = Consumo;
    }

    
    public int getRevolxMin() {
        return RevolxMin;
    }

    public void setRevolxMin(int RevolxMin) {
        this.RevolxMin = RevolxMin;
    }

    
}