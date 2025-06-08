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
public class Aerolinea {
    private String id;
    private String nombre;
    private String direccion;
    private String contacto;
    private String telefono;
    private List<Avion> aviones;

    public Aerolinea() {
    }

    public Aerolinea(String id, String nombre, String direccion, String contacto, String telefono, List<Avion> aviones) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.telefono = telefono;
        this.aviones = aviones;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Avion> getAviones() {
        return aviones;
    }

    public void setAviones(List<Avion> aviones) {
        this.aviones = aviones;
    }
}
