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
    private int capacidad;
    private boolean estado; 
    private int id; 
    private String modelo;
    private double peso;
    private Aerolinea aerolinea;

    public Avion() {
    }

    public Avion(int capacidad, boolean estado,
                 int id, String modelo, double peso, Aerolinea aerolinea) {
        this.capacidad = capacidad;
        this.estado = estado;
        this.id = id;
        this.modelo = modelo;
        this.peso = peso;
        this.aerolinea = aerolinea;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    @Override
    public String toString() {
        return "Avión " + modelo;
    }
}