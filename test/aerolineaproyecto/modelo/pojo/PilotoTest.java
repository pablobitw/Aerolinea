package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PilotoTest {

    @Test
    public void testConstructorYGetters() {
        Piloto piloto = new Piloto("p001", "Miguel", "miguelp", "pass123", "M",
                                   "Piloto", "Calle 123", "1980-12-12", 3000.0,
                                   "ABC123", 15, 2000);

        assertEquals("p001", piloto.getId());
        assertEquals("Miguel", piloto.getNombre());
        assertEquals("miguelp", piloto.getUser());
        assertEquals("pass123", piloto.getPass());
        assertEquals("M", piloto.getGenero());
        assertEquals("Piloto", piloto.getTipoEmpleado());
        assertEquals("Calle 123", piloto.getDireccion());
        assertEquals("1980-12-12", piloto.getFechaNacimiento());
        assertEquals(3000.0, piloto.getSalario(), 0.001);

        assertEquals("ABC123", piloto.getLicencia());
        assertEquals(15, piloto.getAniosExperiencia());
        assertEquals(2000, piloto.getHorasVuelo());
    }

    @Test
    public void testSetters() {
        Piloto piloto = new Piloto();

        piloto.setId("p002");
        piloto.setNombre("Ana");
        piloto.setUser("anap");
        piloto.setPass("pass456");
        piloto.setGenero("F");
        piloto.setTipoEmpleado("Piloto");
        piloto.setDireccion("Avenida 456");
        piloto.setFechaNacimiento("1990-01-01");
        piloto.setSalario(3500.50);

        piloto.setLicencia("XYZ789");
        piloto.setAniosExperiencia(10);
        piloto.setHorasVuelo(1500);

        assertEquals("p002", piloto.getId());
        assertEquals("Ana", piloto.getNombre());
        assertEquals("anap", piloto.getUser());
        assertEquals("pass456", piloto.getPass());
        assertEquals("F", piloto.getGenero());
        assertEquals("Piloto", piloto.getTipoEmpleado());
        assertEquals("Avenida 456", piloto.getDireccion());
        assertEquals("1990-01-01", piloto.getFechaNacimiento());
        assertEquals(3500.50, piloto.getSalario(), 0.001);

        assertEquals("XYZ789", piloto.getLicencia());
        assertEquals(10, piloto.getAniosExperiencia());
        assertEquals(1500, piloto.getHorasVuelo());
    }
}
