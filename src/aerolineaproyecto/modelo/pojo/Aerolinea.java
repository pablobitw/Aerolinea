package aerolineaproyecto.modelo.pojo;

public class Aerolinea {
    private int id;
    private String direccion;
    private String nombre;
    private String contacto;
    private String telefono;

    public Aerolinea() {}

    public Aerolinea(int id, String direccion, String nombre,
                     String contacto, String telefono) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
