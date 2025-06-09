public class Aerolinea {
    private int numeroIdentificacion; // antes: id (String)
    private String direccion;
    private String nombre;
    private String contacto;
    private String telefono;

    public Aerolinea() {}

    public Aerolinea(int numeroIdentificacion, String direccion, String nombre,
                     String contacto, String telefono) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(int numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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

    public void setContacto(String personaContacto) {
        this.contacto = personaContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return numeroIdentificacion + " - " + nombre;
    }
}