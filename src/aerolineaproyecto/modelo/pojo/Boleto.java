package aerolineaproyecto.modelo.pojo;

import java.util.Date;

/**
 *
 * @author PABLO
 */
public class Boleto {
    private String id;
    private int idVuelo;
    private Date fechaCompra;
    private String numAsiento;
    private Vuelo vuelo;
    private Cliente cliente;

    public Boleto() {
    }

    public Boleto(String id, int idVuelo, Date fechaCompra, String numAsiento, Vuelo vuelo, Cliente cliente) {
        this.id = id;
        this.idVuelo = idVuelo;
        this.fechaCompra = fechaCompra;
        this.numAsiento = numAsiento;
        this.vuelo = vuelo;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdVuelo() {
        if (vuelo != null) {
            return vuelo.getId();
        }
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(String numAsiento) {
        this.numAsiento = numAsiento;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
        if (vuelo != null) {
            this.idVuelo = vuelo.getId();
        } else {
            this.idVuelo = 0; // O el valor que consideres para "sin vuelo"
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
