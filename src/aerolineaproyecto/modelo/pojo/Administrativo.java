/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

/**
 *
 * @author PABLO
 */
public class Administrativo extends Empleado {
    private String departamento;
    private int horasTrabajo;

    public Administrativo() {}

    public Administrativo(String id, String nombre, String user, String pass, String genero,
                          String tipoEmpleado, String direccion, String fechaNacimiento, double salario,
                          String departamento, int horasTrabajo) {
        super(id, nombre, user, pass, genero, tipoEmpleado, direccion, fechaNacimiento, salario);
        this.departamento = departamento;
        this.horasTrabajo = horasTrabajo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(int horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
    }
}
