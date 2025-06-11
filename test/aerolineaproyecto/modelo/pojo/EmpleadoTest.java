package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmpleadoTest {

    @Test
    public void testConstructorYGettersSetters() {
        Empleado emp = new Empleado("e001", "Juan Perez", "jperez", "1234", "M", "Piloto",
                                    "Calle Falsa 123", "1990-01-01", 1500.50);

        assertEquals("e001", emp.getId());
        assertEquals("Juan Perez", emp.getNombre());
        assertEquals("jperez", emp.getUser());
        assertEquals("1234", emp.getPass());
        assertEquals("M", emp.getGenero());
        assertEquals("Piloto", emp.getTipoEmpleado());
        assertEquals("Calle Falsa 123", emp.getDireccion());
        assertEquals("1990-01-01", emp.getFechaNacimiento());
        assertEquals(1500.50, emp.getSalario(), 0.001);

        emp.setId("e002");
        emp.setNombre("Carlos Lopez");
        emp.setUser("clopez");
        emp.setPass("abcd");
        emp.setGenero("F");
        emp.setTipoEmpleado("Administrativo");
        emp.setDireccion("Avenida Siempre Viva 456");
        emp.setFechaNacimiento("1985-05-05");
        emp.setSalario(2000.75);

        assertEquals("e002", emp.getId());
        assertEquals("Carlos Lopez", emp.getNombre());
        assertEquals("clopez", emp.getUser());
        assertEquals("abcd", emp.getPass());
        assertEquals("F", emp.getGenero());
        assertEquals("Administrativo", emp.getTipoEmpleado());
        assertEquals("Avenida Siempre Viva 456", emp.getDireccion());
        assertEquals("1985-05-05", emp.getFechaNacimiento());
        assertEquals(2000.75, emp.getSalario(), 0.001);
    }

    @Test
    public void testToString() {
        Empleado emp = new Empleado();
        emp.setNombre("Luis Martinez");
        assertEquals("Luis Martinez", emp.toString());
    }
}
