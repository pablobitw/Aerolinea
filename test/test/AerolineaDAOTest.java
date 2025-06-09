package test;

import static org.junit.Assert.*;
import org.junit.Test;
import aerolineaproyecto.modelo.dao.AerolineaDAO;
import aerolineaproyecto.modelo.pojo.Aerolinea;

import java.util.List;
import java.util.UUID;

public class AerolineaDAOTest {

    @Test
    public void testCargarAerolineasNoNull() {
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertNotNull("La lista de aerolíneas no debe ser null", aerolineas);
    }

    @Test
    public void testAgregarYEliminarAerolinea() {
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        int initialSize = aerolineas.size();

        Aerolinea nueva = new Aerolinea();
        // Suponiendo que Aerolinea tiene un setId, setNombre y otros campos
        String idUnico = UUID.randomUUID().toString();
        nueva.setId(idUnico);
        nueva.setNombre("Test Aerolinea");

        AerolineaDAO.agregarAerolinea(nueva);

        List<Aerolinea> aerolineasDespues = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe haber una aerolínea más después de agregar", initialSize + 1, aerolineasDespues.size());

        // Eliminar la aerolinea para dejar el test limpio
        AerolineaDAO.eliminarAerolinea(idUnico);
        List<Aerolinea> aerolineasFinal = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe regresar al tamaño inicial después de eliminar", initialSize, aerolineasFinal.size());
    }

    @Test
    public void testActualizarAerolinea() {
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        if (aerolineas.isEmpty()) {
            // Si no hay aerolineas, agregar una para probar
            Aerolinea nueva = new Aerolinea();
            String idUnico = UUID.randomUUID().toString();
            nueva.setId(idUnico);
            nueva.setNombre("Test Aerolinea");
            AerolineaDAO.agregarAerolinea(nueva);
            aerolineas = AerolineaDAO.cargarAerolineas();
        }

        Aerolinea aerolinea = aerolineas.get(0);
        String nombreOriginal = aerolinea.getNombre();
        aerolinea.setNombre("Nombre Actualizado");

        AerolineaDAO.actualizarAerolinea(aerolinea);

        List<Aerolinea> aerolineasDespues = AerolineaDAO.cargarAerolineas();
        Aerolinea actualizada = aerolineasDespues.stream()
            .filter(a -> a.getId().equals(aerolinea.getId()))
            .findFirst().orElse(null);

        assertNotNull("Debe encontrar la aerolinea actualizada", actualizada);
        assertEquals("El nombre debe haberse actualizado", "Nombre Actualizado", actualizada.getNombre());

        // Restaurar nombre original para no afectar otros tests
        aerolinea.setNombre(nombreOriginal);
        AerolineaDAO.actualizarAerolinea(aerolinea);
    }
}
