/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

import java.util.List;

/**
 *
 * @author PABLO
 */
public class Vuelo {
    private int numero_pasajeros;
    private double costo_boleto;
    private String tiempo_recorrido;
    private String ciudad_salida;
    private String ciudad_llegada;
    private String salida;
    private String llegada;
    private String avion_id;
    private List<String> pilotos;
    private List<String> asistentes_vuelo;

    public Vuelo() {
    }

    public Vuelo(int numero_pasajeros, double costo_boleto, String tiempo_recorrido,
                 String ciudad_salida, String ciudad_llegada, String salida,
                 String llegada, String avion_id, List<String> pilotos, List<String> asistentes_vuelo) {
        this.numero_pasajeros = numero_pasajeros;
        this.costo_boleto = costo_boleto;
        this.tiempo_recorrido = tiempo_recorrido;
        this.ciudad_salida = ciudad_salida;
        this.ciudad_llegada = ciudad_llegada;
        this.salida = salida;
        this.llegada = llegada;
        this.avion_id = avion_id;
        this.pilotos = pilotos;
        this.asistentes_vuelo = asistentes_vuelo;
    }

    public int getNumero_pasajeros() {
        return numero_pasajeros;
    }

    public void setNumero_pasajeros(int numero_pasajeros) {
        this.numero_pasajeros = numero_pasajeros;
    }

    public double getCosto_boleto() {
        return costo_boleto;
    }

    public void setCosto_boleto(double costo_boleto) {
        this.costo_boleto = costo_boleto;
    }

    public String getTiempo_recorrido() {
        return tiempo_recorrido;
    }

    public void setTiempo_recorrido(String tiempo_recorrido) {
        this.tiempo_recorrido = tiempo_recorrido;
    }

    public String getCiudad_salida() {
        return ciudad_salida;
    }

    public void setCiudad_salida(String ciudad_salida) {
        this.ciudad_salida = ciudad_salida;
    }

    public String getCiudad_llegada() {
        return ciudad_llegada;
    }

    public void setCiudad_llegada(String ciudad_llegada) {
        this.ciudad_llegada = ciudad_llegada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getLlegada() {
        return llegada;
    }

    public void setLlegada(String llegada) {
        this.llegada = llegada;
    }

    public String getAvion_id() {
        return avion_id;
    }

    public void setAvion_id(String avion_id) {
        this.avion_id = avion_id;
    }

    public List<String> getPilotos() {
        return pilotos;
    }

    public void setPilotos(List<String> pilotos) {
        this.pilotos = pilotos;
    }

    public List<String> getAsistentes_vuelo() {
        return asistentes_vuelo;
    }

    public void setAsistentes_vuelo(List<String> asistentes_vuelo) {
        this.asistentes_vuelo = asistentes_vuelo;
    }
}