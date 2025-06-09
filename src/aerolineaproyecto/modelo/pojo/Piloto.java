package aerolineaproyecto.modelo.pojo;

public class Piloto extends Empleado {
    private String licencia;
    private int aniosExperiencia;
    private int horasVuelo;

    public Piloto() {
        super();
    }

    public Piloto(String id, String nombre, String user, String pass, String genero,
                  String tipoEmpleado, String direccion, String fechaNacimiento, double salario,
                  String licencia, int aniosExperiencia, int horasVuelo) {
        super(id, nombre, user, pass, genero, tipoEmpleado, direccion, fechaNacimiento, salario);
        this.licencia = licencia;
        this.aniosExperiencia = aniosExperiencia;
        this.horasVuelo = horasVuelo;
    }

    // Getters y Setters
    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(int horasVuelo) {
        this.horasVuelo = horasVuelo;
    }
}
