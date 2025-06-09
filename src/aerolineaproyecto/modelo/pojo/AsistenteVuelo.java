/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.pojo;

/**
 *
 * @author PABLO
 */
public class AsistenteVuelo extends Empleado {
    private int horasAsistencia;
    private int numIdiomas;

    public AsistenteVuelo() {}

    public AsistenteVuelo(String id, String nombre, String user, String pass, String genero,
                          String tipoEmpleado, String direccion, String fechaNacimiento, double salario,
                          int horasAsistencia, int numIdiomas) {
        super(id, nombre, user, pass, genero, tipoEmpleado, direccion, fechaNacimiento, salario);
        this.horasAsistencia = horasAsistencia;
        this.numIdiomas = numIdiomas;
    }

    public int getHorasAsistencia() {
        return horasAsistencia;
    }

    public void setHorasAsistencia(int horasAsistencia) {
        this.horasAsistencia = horasAsistencia;
    }

    public int getNumIdiomas() {
        return numIdiomas;
    }

    public void setNumIdiomas(int numIdiomas) {
        this.numIdiomas = numIdiomas;
    }
}