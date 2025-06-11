package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class ClienteTest {

    @Test
    public void testConstructorYGettersSetters() {
        LocalDate fecha = LocalDate.of(1990, 5, 20);
        Cliente cliente = new Cliente("Juan", "Pérez", "Peruana", fecha);

        assertEquals("Juan", cliente.getNombres());
        assertEquals("Pérez", cliente.getApellidos());
        assertEquals("Peruana", cliente.getNacionalidad());
        assertEquals(fecha, cliente.getFechaNacimiento());

        cliente.setNombres("Carlos");
        cliente.setApellidos("Lopez");
        cliente.setNacionalidad("Mexicana");
        LocalDate nuevaFecha = LocalDate.of(1985, 1, 15);
        cliente.setFechaNacimiento(nuevaFecha);

        assertEquals("Carlos", cliente.getNombres());
        assertEquals("Lopez", cliente.getApellidos());
        assertEquals("Mexicana", cliente.getNacionalidad());
        assertEquals(nuevaFecha, cliente.getFechaNacimiento());
    }

    @Test
    public void testToString() {
        Cliente cliente = new Cliente("Ana", "Gomez", "Argentina", LocalDate.of(1995, 12, 1));
        assertEquals("Ana Gomez", cliente.toString());
    }

    @Test
    public void testEqualsYHashCode() {
        Cliente cliente1 = new Cliente("Luis", "Martinez", "Chile", LocalDate.of(1992, 7, 10));
        Cliente cliente2 = new Cliente("Luis", "Martinez", "Chile", LocalDate.of(1992, 7, 10));
        Cliente cliente3 = new Cliente("Luis", "Martinez", "Chile", LocalDate.of(1990, 7, 10));

        assertTrue(cliente1.equals(cliente2));
        assertEquals(cliente1.hashCode(), cliente2.hashCode());

        assertFalse(cliente1.equals(cliente3));
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }
}
