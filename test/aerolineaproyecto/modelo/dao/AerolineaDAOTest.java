package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Aerolinea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AerolineaDAOTest {
    
    private File archivoBackup;
    private final String rutaArchivo = "data/aerolineas.json";
    
    @Before
    public void setUp() throws IOException {
        File archivoOriginal = new File(rutaArchivo);
        if (archivoOriginal.exists()) {
            archivoBackup = new File(rutaArchivo + ".backup");
            if (archivoBackup.exists()) {
                archivoBackup.delete();
            }
            archivoOriginal.renameTo(archivoBackup);
        }
        
        File directorio = new File("data");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        String jsonInicial = "[\n" +
            "  {\n" +
            "    \"id\": \"1\",\n" +
            "    \"direccion\": \"Ciudad de Mexico\",\n" +
            "    \"nombre\": \"Aeromexico\",\n" +
            "    \"contacto\": \"contacto@aeromexico.com\",\n" +
            "    \"telefono\": \"555-1234\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"2\",\n" +
            "    \"direccion\": \"Guadalajara\",\n" +
            "    \"nombre\": \"Volaris\",\n" +
            "    \"contacto\": \"info@volaris.com\",\n" +
            "    \"telefono\": \"555-5678\"\n" +
            "  }\n" +
            "]";
        
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(jsonInicial);
        }
    }
    
    @After
    public void tearDown() {
        // Eliminar archivo de prueba
        File archivoPrueba = new File(rutaArchivo);
        if (archivoPrueba.exists()) {
            archivoPrueba.delete();
        }
        
        // Restaurar backup si existía
        if (archivoBackup != null && archivoBackup.exists()) {
            archivoBackup.renameTo(new File(rutaArchivo));
        }
    }
    
    @Test
    public void testCargarAerolineas() {
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        
        assertNotNull("La lista no debe ser null", aerolineas);
        assertEquals("Debe cargar 2 aerolíneas", 2, aerolineas.size());
        System.out.println("✓ Test cargarAerolineas pasado");
    }
    
    @Test
    public void testCargarAerolineas_ArchivoNoExiste() {
        File archivo = new File(rutaArchivo);
        archivo.delete();
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        
        assertNotNull("La lista no debe ser null", aerolineas);
        assertTrue("La lista debe estar vacía", aerolineas.isEmpty());
        System.out.println("✓ Test cargarAerolineas_ArchivoNoExiste pasado");
    }
    
    @Test
    public void testAgregarAerolinea() {
        Aerolinea nuevaAerolinea = new Aerolinea();
        nuevaAerolinea.setId("3");
        nuevaAerolinea.setNombre("VivaAerobus");
        nuevaAerolinea.setDireccion("Monterrey");
        nuevaAerolinea.setContacto("contacto@vivaaerobus.com");
        nuevaAerolinea.setTelefono("555-9999");
        
        AerolineaDAO.agregarAerolinea(nuevaAerolinea);
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe tener 3 aerolíneas", 3, aerolineas.size());
        
        boolean encontrada = false;
        for (Aerolinea a : aerolineas) {
            if ("3".equals(a.getId()) && "VivaAerobus".equals(a.getNombre())) {
                encontrada = true;
                assertEquals("La dirección debe coincidir", "Monterrey", a.getDireccion());
                assertEquals("El contacto debe coincidir", "contacto@vivaaerobus.com", a.getContacto());
                assertEquals("El teléfono debe coincidir", "555-9999", a.getTelefono());
                break;
            }
        }
        assertTrue("La nueva aerolínea debe estar en la lista", encontrada);
        System.out.println("✓ Test agregarAerolinea pasado");
    }
    
    @Test
    public void testActualizarAerolinea() {
        Aerolinea aerolineaActualizada = new Aerolinea();
        aerolineaActualizada.setId("1");
        aerolineaActualizada.setNombre("Aeromexico Connect");
        aerolineaActualizada.setDireccion("Ciudad de Mexico - Actualizada");
        aerolineaActualizada.setContacto("nuevo@aeromexico.com");
        aerolineaActualizada.setTelefono("555-0000");
        
        AerolineaDAO.actualizarAerolinea(aerolineaActualizada);
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe mantener 2 aerolíneas", 2, aerolineas.size());
        
        // Buscar la aerolínea actualizada
        boolean actualizada = false;
        for (Aerolinea a : aerolineas) {
            if ("1".equals(a.getId())) {
                actualizada = true;
                assertEquals("El nombre debe estar actualizado", "Aeromexico Connect", a.getNombre());
                assertEquals("La dirección debe estar actualizada", "Ciudad de Mexico - Actualizada", a.getDireccion());
                assertEquals("El contacto debe estar actualizado", "nuevo@aeromexico.com", a.getContacto());
                assertEquals("El teléfono debe estar actualizado", "555-0000", a.getTelefono());
                break;
            }
        }
        assertTrue("La aerolínea debe estar actualizada", actualizada);
        System.out.println("✓ Test actualizarAerolinea pasado");
    }
    
    @Test
    public void testEliminarAerolinea() {
        AerolineaDAO.eliminarAerolinea("1");
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe quedar 1 aerolínea", 1, aerolineas.size());
        
        // Verificar que no existe la aerolínea eliminada
        boolean encontrada = false;
        for (Aerolinea a : aerolineas) {
            if ("1".equals(a.getId())) {
                encontrada = true;
                break;
            }
        }
        assertFalse("La aerolínea eliminada no debe existir", encontrada);
        System.out.println("✓ Test eliminarAerolinea pasado");
    }
    
    @Test
    public void testEliminarAerolinea_NoExiste() {
        AerolineaDAO.eliminarAerolinea("999");
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe mantener 2 aerolíneas", 2, aerolineas.size());
        System.out.println("✓ Test eliminarAerolinea_NoExiste pasado");
    }
    
    @Test
    public void testGuardarAerolineas() {
        List<Aerolinea> nuevasAerolineas = new ArrayList<>();
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setId("100");
        aerolinea.setNombre("Test Airline");
        aerolinea.setDireccion("Test City");
        aerolinea.setContacto("test@airline.com");
        aerolinea.setTelefono("555-TEST");
        nuevasAerolineas.add(aerolinea);
        
        AerolineaDAO.guardarAerolineas(nuevasAerolineas);
        
        List<Aerolinea> aerolineasCargadas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe tener 1 aerolínea", 1, aerolineasCargadas.size());
        
        Aerolinea cargada = aerolineasCargadas.get(0);
        assertEquals("El nombre debe coincidir", "Test Airline", cargada.getNombre());
        assertEquals("La dirección debe coincidir", "Test City", cargada.getDireccion());
        assertEquals("El contacto debe coincidir", "test@airline.com", cargada.getContacto());
        assertEquals("El teléfono debe coincidir", "555-TEST", cargada.getTelefono());
        System.out.println("✓ Test guardarAerolineas pasado");
    }
    
    @Test 
    public void testActualizarAerolineaInexistente() {
        Aerolinea aerolineaInexistente = new Aerolinea();
        aerolineaInexistente.setId("999");
        aerolineaInexistente.setNombre("No Existe");
        aerolineaInexistente.setDireccion("Nowhere");
        aerolineaInexistente.setContacto("noexist@nowhere.com");
        aerolineaInexistente.setTelefono("000-0000");
        
        AerolineaDAO.actualizarAerolinea(aerolineaInexistente);
        
        List<Aerolinea> aerolineas = AerolineaDAO.cargarAerolineas();
        assertEquals("Debe mantener 2 aerolíneas", 2, aerolineas.size());
        
        boolean encontrada = false;
        for (Aerolinea a : aerolineas) {
            if ("999".equals(a.getId())) {
                encontrada = true;
                break;
            }
        }
        assertFalse("No debe agregar aerolínea inexistente", encontrada);
        System.out.println("✓ Test actualizarAerolineaInexistente pasado");
    }
}