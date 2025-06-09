/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

/**
 *
 * @author PABLO
 */
public class Avion {
    private String id;
    private int capacidad;
    private String modelo;
    private double peso;

    public Avion() {
    }

    public Avion(String id, int capacidad, String modelo, double peso) {
        this.id = id;
        this.capacidad = capacidad;
        this.modelo = modelo;
        this.peso = peso;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    @Override
public String toString() {
    return "Avión " + modelo ;
}

}
