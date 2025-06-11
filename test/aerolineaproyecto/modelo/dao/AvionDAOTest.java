package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Aerolinea;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas unitarias para la clase AvionDAO usando JUnit 4
 */
public class AvionDAOTest {
    
    private String archivoOriginal;
    private String archivoTemporal;
    private File directorioTemporal;
    
    @Before
    public void setUp() throws Exception {
        directorioTemporal = Files.createTempDirectory("aviones_test").toFile();
        
        archivoOriginal = AvionDAO.getArchivoJson();
        
        archivoTemporal = new File(directorioTemporal, "aviones_test.json").getAbsolutePath();
        AvionDAO.setArchivoJson(archivoTemporal);
    }
    
    @After
    public void tearDown() throws Exception {
        AvionDAO.setArchivoJson(archivoOriginal);
        
        eliminarDirectorioRecursivo(directorioTemporal);
    }
    
    private void eliminarDirectorioRecursivo(File directorio) {
        if (directorio.exists()) {
            File[] archivos = directorio.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        eliminarDirectorioRecursivo(archivo);
                    } else {
                        archivo.delete();
                    }
                }
            }
            directorio.delete();
        }
    }
    
    @Test
    public void testCargarAvionesArchivoNoExiste() {
        List<Avion> aviones = AvionDAO.cargarAviones();
        assertNotNull(aviones);
        assertTrue(aviones.isEmpty());
    }
    
    @Test
    public void testGuardarYCargarAvionesVacio() {
        List<Avion> avionesVacio = new ArrayList<Avion>();
        
        AvionDAO.guardarAviones(avionesVacio);
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertNotNull(avionesLeidos);
        assertTrue(avionesLeidos.isEmpty());
    }
    
    @Test
    public void testGuardarYCargarUnAvion() {
        Aerolinea aerolinea = new Aerolinea();
        
        Avion avion = new Avion(180, true, "AV001", "Boeing 737", 79015.8, aerolinea);
        List<Avion> aviones = new ArrayList<Avion>();
        aviones.add(avion);
        
        AvionDAO.guardarAviones(aviones);
        
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertNotNull(avionesLeidos);
        assertEquals(1, avionesLeidos.size());
        
        Avion avionLeido = avionesLeidos.get(0);
        assertEquals(180, avionLeido.getCapacidad());
        assertTrue(avionLeido.isEstado());
        assertEquals("AV001", avionLeido.getId());
        assertEquals("Boeing 737", avionLeido.getModelo());
        assertEquals(79015.8, avionLeido.getPeso(), 0.001);
    }
    
    @Test
    public void testGuardarYCargarMultiplesAviones() {
        Aerolinea aerolinea1 = new Aerolinea();
        Aerolinea aerolinea2 = new Aerolinea();
        
        List<Avion> aviones = new ArrayList<Avion>();
        aviones.add(new Avion(180, true, "AV001", "Boeing 737", 79015.8, aerolinea1));
        aviones.add(new Avion(220, false, "AV002", "Airbus A320", 66000.0, aerolinea2));
        aviones.add(new Avion(400, true, "AV003", "Boeing 747", 183500.0, aerolinea1));
        
        AvionDAO.guardarAviones(aviones);
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertNotNull(avionesLeidos);
        assertEquals(3, avionesLeidos.size());
        

        Avion avion1 = avionesLeidos.get(0);
        assertEquals(180, avion1.getCapacidad());
        assertTrue(avion1.isEstado());
        assertEquals("AV001", avion1.getId());
        assertEquals("Boeing 737", avion1.getModelo());
        
    
        Avion avion2 = avionesLeidos.get(1);
        assertEquals(220, avion2.getCapacidad());
        assertFalse(avion2.isEstado());
        assertEquals("AV002", avion2.getId());
        assertEquals("Airbus A320", avion2.getModelo());
        
    
        Avion avion3 = avionesLeidos.get(2);
        assertEquals(400, avion3.getCapacidad());
        assertTrue(avion3.isEstado());
        assertEquals("AV003", avion3.getId());
        assertEquals("Boeing 747", avion3.getModelo());
    }
    
    @Test
    public void testGuardarAvionConDatosNulos() {
        List<Avion> aviones = new ArrayList<Avion>();
        Avion avionConNulos = new Avion(0, false, null, null, 0.0, null);
        aviones.add(avionConNulos);
        
        AvionDAO.guardarAviones(aviones);
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertNotNull(avionesLeidos);
        assertEquals(1, avionesLeidos.size());
        
        Avion avionLeido = avionesLeidos.get(0);
        assertEquals(0, avionLeido.getCapacidad());
        assertFalse(avionLeido.isEstado());
        assertNull(avionLeido.getId());
        assertNull(avionLeido.getModelo());
        assertEquals(0.0, avionLeido.getPeso(), 0.001);
        assertNull(avionLeido.getAerolinea());
    }
    
    @Test
    public void testArchivoSeCreaCorrectamente() {
        List<Avion> aviones = new ArrayList<Avion>();
        aviones.add(new Avion(100, true, "TEST", "Test Model", 50000.0, null));
        
        AvionDAO.guardarAviones(aviones);
        
        File archivo = new File(archivoTemporal);
        assertTrue(archivo.exists());
        assertTrue(archivo.isFile());
        assertTrue(archivo.length() > 0);
    }
    
    @Test
    public void testCreacionDirectorioSiNoExiste() throws Exception {
        // Crear ruta con directorio que no existe
        File nuevoDirectorio = new File(directorioTemporal, "nuevo_directorio");
        String archivoEnDirectorioNuevo = new File(nuevoDirectorio, "aviones.json").getAbsolutePath();
        
        // Cambiar temporalmente la ruta del archivo
        String archivoAnterior = AvionDAO.getArchivoJson();
        AvionDAO.setArchivoJson(archivoEnDirectorioNuevo);
        
        try {
            List<Avion> aviones = new ArrayList<Avion>();
            aviones.add(new Avion(150, true, "DIR001", "Test Directory", 60000.0, null));
            
            AvionDAO.guardarAviones(aviones);
            
            
            assertTrue(nuevoDirectorio.exists());
            assertTrue(new File(archivoEnDirectorioNuevo).exists());
            
        } finally {
     
            AvionDAO.setArchivoJson(archivoAnterior);
        }
    }
    
    @Test
    public void testPersistenciaDeEstados() {
        List<Avion> aviones = new ArrayList<Avion>();
        aviones.add(new Avion(100, true, "EST001", "Estado True", 50000.0, null));
        aviones.add(new Avion(200, false, "EST002", "Estado False", 60000.0, null));
        
        AvionDAO.guardarAviones(aviones);
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertEquals(2, avionesLeidos.size());
        assertTrue(avionesLeidos.get(0).isEstado());
        assertFalse(avionesLeidos.get(1).isEstado());
    }
    
    @Test
    public void testIntegridadDatos() {
        // Crear datos de prueba con valores específicos
        Aerolinea aerolinea = new Aerolinea();
        List<Avion> avionesOriginales = new ArrayList<Avion>();
        
        Avion avion1 = new Avion(150, true, "INT001", "Boeing 737-800", 79015.8, aerolinea);
        Avion avion2 = new Avion(180, false, "INT002", "Airbus A320-200", 66000.0, aerolinea);
        
        avionesOriginales.add(avion1);
        avionesOriginales.add(avion2);
        
      
        AvionDAO.guardarAviones(avionesOriginales);
        List<Avion> avionesLeidos = AvionDAO.cargarAviones();
        
        assertEquals(avionesOriginales.size(), avionesLeidos.size());
        
        for (int i = 0; i < avionesOriginales.size(); i++) {
            Avion original = avionesOriginales.get(i);
            Avion leido = avionesLeidos.get(i);
            
            assertEquals(original.getCapacidad(), leido.getCapacidad());
        }
    }
}
