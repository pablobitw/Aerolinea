import aerolineaproyecto.modelo.dao.EmpleadoDAO;
import aerolineaproyecto.modelo.pojo.Empleado;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmpleadoDAOTest {

    @Test
    public void testCargarEmpleados() {
        List<Empleado> empleados = EmpleadoDAO.cargarEmpleados();

        assertNotNull("La lista de empleados no debe ser null", empleados);
        // No evaluamos contenido porque no sabemos si hay empleados.json
    }

    @Test
    public void testGuardarEmpleadosVacio() {
        // Creamos lista vacía, solo para verificar que no cause error al guardar
        List<Empleado> empleados = new ArrayList<>();

        try {
            EmpleadoDAO.guardarEmpleados(empleados);
        } catch (Exception e) {
            fail("No se debe lanzar excepción al guardar lista vacía: " + e.getMessage());
        }
    }

    @Test
    public void testGuardarYCargarMismoTamano() {
        // Creamos una lista con al menos un Empleado vacío
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado());  // Suponiendo que tenga constructor vacío

        EmpleadoDAO.guardarEmpleados(empleados);

        List<Empleado> cargados = EmpleadoDAO.cargarEmpleados();

        assertNotNull("La lista cargada no debe ser null", cargados);
        assertEquals("La lista cargada debe tener el mismo tamaño que la guardada", empleados.size(), cargados.size());
    }
}
