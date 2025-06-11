/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author PABLO
 */
public class Cliente {
    private String nombres;
    private String apellidos;
    private String nacionalidad;
    private LocalDate fechaNacimiento;

    public Cliente() {
    }

    public Cliente(String nombres, String apellidos, String nacionalidad, LocalDate fechaNacimiento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    @Override
public String toString() {
    return getNombres() + " " + getApellidos();
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    
    Cliente other = (Cliente) obj;
    return nombres.equals(other.nombres) &&
           apellidos.equals(other.apellidos) &&
           fechaNacimiento.equals(other.fechaNacimiento);
}

@Override
public int hashCode() {
    return java.util.Objects.hash(nombres, apellidos, fechaNacimiento);
}

}
