/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

/**
 *
 * @author PABLO
 */
public class Asiento {
    private String numeroAsiento;
    private boolean ocupado;
    private String claseAsiento; 
    private int fila;
    private String columna;
    
    public Asiento(String numeroAsiento, boolean ocupado, String claseAsiento) {
        this.numeroAsiento = numeroAsiento;
        this.ocupado = ocupado;
        this.claseAsiento = claseAsiento;
        
        if (numeroAsiento != null && numeroAsiento.length() >= 2) {
            this.fila = Integer.parseInt(numeroAsiento.substring(0, numeroAsiento.length() - 1));
            this.columna = numeroAsiento.substring(numeroAsiento.length() - 1);
        }
    }
    
    public String getNumeroAsiento() {
        return numeroAsiento;
    }
    
    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }
    
    public boolean getOcupado() {
        return ocupado;
    }
    
    public void setEstaOcupado(boolean estaOcupado) {
        this.ocupado = ocupado;
    }
    
    public String getClaseAsiento() {
        return claseAsiento;
    }
    
    public void setClaseAsiento(String claseAsiento) {
        this.claseAsiento = claseAsiento;
    }
    
    public int getFila() {
        return fila;
    }
    
    public String getColumna() {
        return columna;
    }
}
