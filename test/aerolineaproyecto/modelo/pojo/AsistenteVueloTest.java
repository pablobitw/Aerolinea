package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AsistenteVueloTest {

    @Test
    public void testConstructorYGetters() {
        AsistenteVuelo asistente = new AsistenteVuelo(
                "001", "Ana Perez", "ana123", "pass",
                "F", "Asistente", "Calle Falsa 123", "1990-05-01",
                1500.0, 40, 3
        );

        assertEquals("001", asistente.getId());
        assertEquals("Ana Perez", asistente.getNombre());
        assertEquals("ana123", asistente.getUser());
        assertEquals("pass", asistente.getPass());
        assertEquals("F", asistente.getGenero());
        assertEquals("Asistente", asistente.getTipoEmpleado());
        assertEquals("Calle Falsa 123", asistente.getDireccion());
        assertEquals("1990-05-01", asistente.getFechaNacimiento());
        assertEquals(1500.0, asistente.getSalario(), 0.01);

        assertEquals(40, asistente.getHorasAsistencia());
        assertEquals(3, asistente.getNumIdiomas());
    }

    @Test
    public void testSetters() {
        AsistenteVuelo asistente = new AsistenteVuelo();
        asistente.setHorasAsistencia(35);
        asistente.setNumIdiomas(2);

        assertEquals(35, asistente.getHorasAsistencia());
        assertEquals(2, asistente.getNumIdiomas());

        asistente.setId("002");
        asistente.setNombre("Juan López");
        asistente.setUser("juanl");
        asistente.setPass("1234");
        asistente.setGenero("M");
        asistente.setTipoEmpleado("Asistente");
        asistente.setDireccion("Av. Siempre Viva 742");
        asistente.setFechaNacimiento("1985-11-15");
        asistente.setSalario(1800.0);

        assertEquals("002", asistente.getId());
        assertEquals("Juan López", asistente.getNombre());
        assertEquals("juanl", asistente.getUser());
        assertEquals("1234", asistente.getPass());
        assertEquals("M", asistente.getGenero());
        assertEquals("Asistente", asistente.getTipoEmpleado());
        assertEquals("Av. Siempre Viva 742", asistente.getDireccion());
        assertEquals("1985-11-15", asistente.getFechaNacimiento());
        assertEquals(1800.0, asistente.getSalario(), 0.01);
    }
}
