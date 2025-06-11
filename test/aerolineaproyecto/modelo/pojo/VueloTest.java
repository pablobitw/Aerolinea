package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class VueloTest {

    @Test
    public void testConstructorYGetters() {
        Avion avion = new Avion();
        avion.setModelo("Boeing 747");

        Empleado piloto1 = new Empleado();
        piloto1.setNombre("Juan");

        Empleado piloto2 = new Empleado();
        piloto2.setNombre("Carlos");

        Empleado asistente1 = new Empleado();
        asistente1.setNombre("Ana");

        Empleado asistente2 = new Empleado();
        asistente2.setNombre("Laura");

        Empleado asistente3 = new Empleado();
        asistente3.setNombre("Miguel");

        Empleado asistente4 = new Empleado();
        asistente4.setNombre("Sofia");

        Empleado piloto = new Empleado();
        piloto.setNombre("Pedro");

        Empleado asistenteVuelo = new Empleado();
        asistenteVuelo.setNombre("Lucia");

        LocalDate fechaSalida = LocalDate.of(2025, 6, 10);
        LocalTime horaSalida = LocalTime.of(14, 30);
        LocalDate fechaLlegada = LocalDate.of(2025, 6, 10);
        LocalTime horaLlegada = LocalTime.of(18, 45);

        Vuelo vuelo = new Vuelo(
            1,
            150,
            250.75,
            "4h 15m",
            "Lima",
            "Buenos Aires",
            fechaSalida,
            horaSalida,
            fechaLlegada,
            horaLlegada,
            avion,
            piloto1, piloto2,
            asistente1, asistente2, asistente3, asistente4,
            piloto,
            asistenteVuelo
        );

        assertEquals(1, vuelo.getId());
        assertEquals(150, vuelo.getNumeroPasajeros());
        assertEquals(250.75, vuelo.getCostoBoleto(), 0.001);
        assertEquals("4h 15m", vuelo.getTiempoRecorrido());
        assertEquals("Lima", vuelo.getCiudadSalida());
        assertEquals("Buenos Aires", vuelo.getCiudadLlegada());

        assertEquals(fechaSalida, vuelo.getFechaSalida());
        assertEquals(horaSalida, vuelo.getHoraSalida());
        assertEquals(fechaLlegada, vuelo.getFechaLlegada());
        assertEquals(horaLlegada, vuelo.getHoraLlegada());

        assertEquals(avion, vuelo.getAvion());

        assertEquals(piloto1, vuelo.getPiloto1());
        assertEquals(piloto2, vuelo.getPiloto2());
        assertEquals(asistente1, vuelo.getAsistente1());
        assertEquals(asistente2, vuelo.getAsistente2());
        assertEquals(asistente3, vuelo.getAsistente3());
        assertEquals(asistente4, vuelo.getAsistente4());
        assertEquals(piloto, vuelo.getPiloto());
        assertEquals(asistenteVuelo, vuelo.getAsistenteVuelo());
    }

    @Test
    public void testSettersYMetodosAuxiliares() {
        Vuelo vuelo = new Vuelo();

        vuelo.setNumeroPasajeros(100);
        assertEquals(100, vuelo.getNumeroPasajeros());

        LocalDate fechaSalida = LocalDate.of(2025, 12, 25);
        LocalTime horaSalida = LocalTime.of(10, 0);
        vuelo.setFechaSalida(fechaSalida);
        vuelo.setHoraSalida(horaSalida);

        assertEquals("25/12/2025 10:00", vuelo.getSalida());

        LocalDate fechaLlegada = LocalDate.of(2025, 12, 25);
        LocalTime horaLlegada = LocalTime.of(14, 30);
        vuelo.setFechaLlegada(fechaLlegada);
        vuelo.setHoraLlegada(horaLlegada);

        assertEquals("25/12/2025 14:30", vuelo.getLlegada());

        Empleado piloto1 = new Empleado();
        piloto1.setNombre("Juan");
        Empleado piloto2 = new Empleado();
        piloto2.setNombre("Carlos");
        Empleado piloto = new Empleado();
        piloto.setNombre("Pedro");

        vuelo.setPiloto1(piloto1);
        vuelo.setPiloto2(piloto2);
        vuelo.setPiloto(piloto);

        assertEquals("Juan, Carlos, Pedro", vuelo.getPilotosNombres());

        Empleado asistente1 = new Empleado();
        asistente1.setNombre("Ana");
        Empleado asistente2 = new Empleado();
        asistente2.setNombre("Laura");
        Empleado asistenteVuelo = new Empleado();
        asistenteVuelo.setNombre("Lucia");

        vuelo.setAsistente1(asistente1);
        vuelo.setAsistente2(asistente2);
        vuelo.setAsistenteVuelo(asistenteVuelo);

        assertEquals("Ana, Laura, Lucia", vuelo.getAsistentesNombres());

        Avion avion = new Avion();
        avion.setModelo("Airbus A320");
        vuelo.setAvion(avion);

        assertEquals("Airbus A320", vuelo.getModeloAvion());

        // Prueba con avion null
        vuelo.setAvion(null);
        assertEquals("N/A", vuelo.getModeloAvion());
    }
}
