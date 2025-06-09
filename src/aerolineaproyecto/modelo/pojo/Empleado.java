package aerolineaproyecto.modelo.pojo;

public class Empleado {
    private String id;
    private String nombre;
    private String user;
    private String pass;
    private String genero;
    private String tipoEmpleado;
    private String direccion;
    private String fechaNacimiento;
    private double salario;

    public Empleado() {
    }

    public Empleado(String id, String nombre, String user, String pass, String genero,
                    String tipoEmpleado, String direccion, String fechaNacimiento, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.genero = genero;
        this.tipoEmpleado = tipoEmpleado;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.salario = salario;
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
    @Override
public String toString() {
    return nombre;
}

}
