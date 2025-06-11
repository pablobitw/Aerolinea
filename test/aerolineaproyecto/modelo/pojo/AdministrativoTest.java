package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AdministrativoTest {

    @Test
    public void testConstructorConParametros() {
        Administrativo admin = new Administrativo(
                "A001", "Carla Ruiz", "carla", "admin123", "F", "Administrativo",
                "Av. Central 123", "1990-05-15", 15000.0, "Finanzas", 40
        );

        assertEquals("A001", admin.getId());
        assertEquals("Carla Ruiz", admin.getNombre());
        assertEquals("carla", admin.getUser());
        assertEquals("admin123", admin.getPass());
        assertEquals("F", admin.getGenero());
        assertEquals("Administrativo", admin.getTipoEmpleado());
        assertEquals("Av. Central 123", admin.getDireccion());
        assertEquals("1990-05-15", admin.getFechaNacimiento());
        assertEquals(15000.0, admin.getSalario(), 0.001);
        assertEquals("Finanzas", admin.getDepartamento());
        assertEquals(40, admin.getHorasTrabajo());
    }

    @Test
    public void testSettersYGetters() {
        Administrativo admin = new Administrativo();

        admin.setId("A002");
        admin.setNombre("Luis Pérez");
        admin.setUser("luisp");
        admin.setPass("admin456");
        admin.setGenero("M");
        admin.setTipoEmpleado("Administrativo");
        admin.setDireccion("Calle 5 #321");
        admin.setFechaNacimiento("1985-12-01");
        admin.setSalario(18000.0);
        admin.setDepartamento("Recursos Humanos");
        admin.setHorasTrabajo(35);

        assertEquals("A002", admin.getId());
        assertEquals("Luis Pérez", admin.getNombre());
        assertEquals("luisp", admin.getUser());
        assertEquals("admin456", admin.getPass());
        assertEquals("M", admin.getGenero());
        assertEquals("Administrativo", admin.getTipoEmpleado());
        assertEquals("Calle 5 #321", admin.getDireccion());
        assertEquals("1985-12-01", admin.getFechaNacimiento());
        assertEquals(18000.0, admin.getSalario(), 0.001);
        assertEquals("Recursos Humanos", admin.getDepartamento());
        assertEquals(35, admin.getHorasTrabajo());
    }
}
