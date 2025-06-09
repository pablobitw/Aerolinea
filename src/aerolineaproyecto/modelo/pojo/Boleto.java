/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;
import java.util.Date;
/**
 *
 * @author PABLO
 */


public class Boleto {
    private String id;
    private Date fechaCompra;
    private String numeroAsiento;
    private Vuelo vuelo;
    private Cliente cliente;

    public Boleto() {
    }

    public Boleto(String id, Date fechaCompra, String numeroAsiento, Vuelo vuelo, Cliente cliente) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.numeroAsiento = numeroAsiento;
        this.vuelo = vuelo;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
