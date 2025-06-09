package aerolineaproyecto.modelo.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Vuelo {


    private int id;
    private int numeroPasajeros;
    private double costoBoleto;
    private String tiempoRecorrido;
    private String ciudadSalida;
    private String ciudadLlegada;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegada;
    private LocalTime horaLlegada;

   
    private Avion avion;  

    
    private Empleado piloto1;
    private Empleado piloto2;
    private Empleado asistente1;
    private Empleado asistente2;
    private Empleado asistente3;
    private Empleado asistente4;

  
    private Empleado piloto;
    private Empleado asistenteVuelo;


    public Vuelo() {}

   
    public Vuelo(int id, int numeroPasajeros, double costoBoleto, String tiempoRecorrido,
                 String ciudadSalida, String ciudadLlegada, LocalDate fechaSalida, LocalTime horaSalida,
                 LocalDate fechaLlegada, LocalTime horaLlegada,
                 Avion avion,  // <-- nuevo parámetro
                 Empleado piloto1, Empleado piloto2,
                 Empleado asistente1, Empleado asistente2, Empleado asistente3, Empleado asistente4,
                 Empleado piloto, Empleado asistenteVuelo) {
        this.id = id;
        this.numeroPasajeros = numeroPasajeros;
        this.costoBoleto = costoBoleto;
        this.tiempoRecorrido = tiempoRecorrido;
        this.ciudadSalida = ciudadSalida;
        this.ciudadLlegada = ciudadLlegada;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.avion = avion;  
        this.piloto1 = piloto1;
        this.piloto2 = piloto2;
        this.asistente1 = asistente1;
        this.asistente2 = asistente2;
        this.asistente3 = asistente3;
        this.asistente4 = asistente4;
        this.piloto = piloto;
        this.asistenteVuelo = asistenteVuelo;
    }

  

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumeroPasajeros() { return numeroPasajeros; }
    public void setNumeroPasajeros(int numeroPasajeros) { this.numeroPasajeros = numeroPasajeros; }

    public double getCostoBoleto() { return costoBoleto; }
    public void setCostoBoleto(double costoBoleto) { this.costoBoleto = costoBoleto; }

    public String getTiempoRecorrido() { return tiempoRecorrido; }
    public void setTiempoRecorrido(String tiempoRecorrido) { this.tiempoRecorrido = tiempoRecorrido; }

    public String getCiudadSalida() { return ciudadSalida; }
    public void setCiudadSalida(String ciudadSalida) { this.ciudadSalida = ciudadSalida; }

    public String getCiudadLlegada() { return ciudadLlegada; }
    public void setCiudadLlegada(String ciudadLlegada) { this.ciudadLlegada = ciudadLlegada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }

    public LocalDate getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDate fechaLlegada) { this.fechaLlegada = fechaLlegada; }

    public LocalTime getHoraLlegada() { return horaLlegada; }
    public void setHoraLlegada(LocalTime horaLlegada) { this.horaLlegada = horaLlegada; }

    // Getter y setter para avion
    public Avion getAvion() { return avion; }
    public void setAvion(Avion avion) { this.avion = avion; }

    public Empleado getPiloto1() { return piloto1; }
    public void setPiloto1(Empleado piloto1) { this.piloto1 = piloto1; }

    public Empleado getPiloto2() { return piloto2; }
    public void setPiloto2(Empleado piloto2) { this.piloto2 = piloto2; }

    public Empleado getAsistente1() { return asistente1; }
    public void setAsistente1(Empleado asistente1) { this.asistente1 = asistente1; }

    public Empleado getAsistente2() { return asistente2; }
    public void setAsistente2(Empleado asistente2) { this.asistente2 = asistente2; }

    public Empleado getAsistente3() { return asistente3; }
    public void setAsistente3(Empleado asistente3) { this.asistente3 = asistente3; }

    public Empleado getAsistente4() { return asistente4; }
    public void setAsistente4(Empleado asistente4) { this.asistente4 = asistente4; }

    public Empleado getPiloto() { return piloto; }
    public void setPiloto(Empleado piloto) { this.piloto = piloto; }

    public Empleado getAsistenteVuelo() { return asistenteVuelo; }
    public void setAsistenteVuelo(Empleado asistenteVuelo) { this.asistenteVuelo = asistenteVuelo; }

    // Métodos de utilidad

    public String getSalida() {
        if (fechaSalida == null || horaSalida == null) return "";
        return fechaSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
               horaSalida.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getLlegada() {
        if (fechaLlegada == null || horaLlegada == null) return "";
        return fechaLlegada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
               horaLlegada.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getPilotosNombres() {
        StringBuilder sb = new StringBuilder();
        if (piloto1 != null && piloto1.getNombre() != null) sb.append(piloto1.getNombre());
        if (piloto2 != null && piloto2.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(piloto2.getNombre());
        }
        if (piloto != null && piloto.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(piloto.getNombre());
        }
        return sb.toString();
    }

    public String getAsistentesNombres() {
        StringBuilder sb = new StringBuilder();
        if (asistente1 != null && asistente1.getNombre() != null) sb.append(asistente1.getNombre());
        if (asistente2 != null && asistente2.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(asistente2.getNombre());
        }
        if (asistente3 != null && asistente3.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(asistente3.getNombre());
        }
        if (asistente4 != null && asistente4.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(asistente4.getNombre());
        }
        if (asistenteVuelo != null && asistenteVuelo.getNombre() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(asistenteVuelo.getNombre());
        }
        return sb.toString();
    }

    
   public String getModeloAvion() {
    if (this.avion != null) {
        return this.avion.getModelo();
    }
    return "N/A";
}
}
