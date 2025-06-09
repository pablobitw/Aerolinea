import aerolineaproyecto.modelo.dao.AvionDAO;
import aerolineaproyecto.modelo.pojo.Avion;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class AvionDAOTest {

    @Test
    public void testCargarAviones() {
        List<Avion> aviones = AvionDAO.cargarAviones();

        // Verifica que la lista no sea nula
        assertNotNull("La lista de aviones no debe ser null", aviones);

        // Verifica que la lista contenga al menos un elemento
        assertFalse("La lista de aviones no debe estar vacía", aviones.isEmpty());
    }

    @Test
    public void testGuardarAviones() {
        List<Avion> avionesOriginales = AvionDAO.cargarAviones();

        try {
            AvionDAO.guardarAviones(avionesOriginales);
        } catch (Exception e) {
            fail("Guardar aviones lanzó excepción: " + e.getMessage());
        }
    }
}
