    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package aerolineaproyecto.modelo.pojo;

    /**
     *
     * @author PABLO
     */

public class Empleado {
    // Campos comunes
    private String id;
    private String nombre;
    private String user;
    private String pass;
    private String genero;
    private String rol;
    private String tipoEmpleado;
    private String direccion;
    private String fechaNacimiento;
    private double salario;

    // Campos para Administrativo
    private String departamento;
    private int horasTrabajo;

    // Campos para AsistenteVuelo
    private Integer horasAsistencia; // Usar Integer para permitir null
    private Integer numIdiomas;

    // Campos para Piloto
    private String licencia;
    private Integer aniosExperiencia;
    private Integer horasVuelo;

    public Empleado() {}

    public Empleado(String id, String nombre, String user, String pass, String genero, String rol, String tipoEmpleado,
                    String direccion, String fechaNacimiento, double salario,
                    String departamento, Integer horasTrabajo,
                    Integer horasAsistencia, Integer numIdiomas,
                    String licencia, Integer aniosExperiencia, Integer horasVuelo) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.genero = genero;
        this.rol = rol;
        this.tipoEmpleado = tipoEmpleado;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.salario = salario;
        this.departamento = departamento;
        this.horasTrabajo = (horasTrabajo == null) ? 0 : horasTrabajo;
        this.horasAsistencia = horasAsistencia;
        this.numIdiomas = numIdiomas;
        this.licencia = licencia;
        this.aniosExperiencia = aniosExperiencia;
        this.horasVuelo = horasVuelo;
    }

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
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

    public Integer getHorasAsistencia() {
        return horasAsistencia;
    }

    public void setHorasAsistencia(Integer horasAsistencia) {
        this.horasAsistencia = horasAsistencia;
    }

    public Integer getNumIdiomas() {
        return numIdiomas;
    }

    public void setNumIdiomas(Integer numIdiomas) {
        this.numIdiomas = numIdiomas;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public Integer getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(Integer horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

}