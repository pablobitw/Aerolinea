package aerolineaproyecto.modelo.pojo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase Avion usando JUnit 4
 */
public class AvionTest {
    
    private Avion avion;
    private Aerolinea aerolinea;
    
    @Before
    public void setUp() {
        // Crear una aerolínea de prueba
        aerolinea = new Aerolinea(); // Asumiendo que existe constructor vacío
        avion = new Avion();
    }
    
    @Test
    public void testConstructorVacio() {
        Avion avionVacio = new Avion();
        assertNotNull(avionVacio);
        assertEquals(0, avionVacio.getCapacidad());
        assertNull(avionVacio.getId());
        assertNull(avionVacio.getModelo());
        assertEquals(0.0, avionVacio.getPeso(), 0.001);
        assertNull(avionVacio.getAerolinea());
    }
    
    @Test
    public void testConstructorCompleto() {
        Avion avionCompleto = new Avion(180, true, "AV001", "Boeing 737", 79015.8, aerolinea);
        
        assertEquals(180, avionCompleto.getCapacidad());
        assertEquals("AV001", avionCompleto.getId());
        assertEquals("Boeing 737", avionCompleto.getModelo());
        assertEquals(79015.8, avionCompleto.getPeso(), 0.001);
        assertEquals(aerolinea, avionCompleto.getAerolinea());
    }
    
    @Test
    public void testSettersYGetters() {
        // Test capacidad
        avion.setCapacidad(200);
        assertEquals(200, avion.getCapacidad());
        
        // Test ID
        avion.setId("AV002");
        assertEquals("AV002", avion.getId());
        
        // Test modelo
        avion.setModelo("Airbus A320");
        assertEquals("Airbus A320", avion.getModelo());
        
        // Test peso
        avion.setPeso(66000.0);
        assertEquals(66000.0, avion.getPeso(), 0.001);
        
        // Test aerolínea
        avion.setAerolinea(aerolinea);
        assertEquals(aerolinea, avion.getAerolinea());
    }
    
    @Test
    public void testCapacidadNegativa() {
        avion.setCapacidad(-50);
        assertEquals(-50, avion.getCapacidad());
        // Nota: Podrías agregar validación en el setter para evitar valores negativos
    }
    
    @Test
    public void testCapacidadCero() {
        avion.setCapacidad(0);
        assertEquals(0, avion.getCapacidad());
    }
    
    @Test
    public void testPesoNegativo() {
        avion.setPeso(-1000.0);
        assertEquals(-1000.0, avion.getPeso(), 0.001);
        // Nota: Podrías agregar validación en el setter para evitar valores negativos
    }
    
    @Test
    public void testIdNulo() {
        avion.setId(null);
        assertNull(avion.getId());
    }
    
    @Test
    public void testIdVacio() {
        avion.setId("");
        assertEquals("", avion.getId());
    }
    
    @Test
    public void testModeloNulo() {
        avion.setModelo(null);
        assertNull(avion.getModelo());
    }
    
    @Test
    public void testModeloVacio() {
        avion.setModelo("");
        assertEquals("", avion.getModelo());
    }
    
    @Test
    public void testAerolineaNula() {
        avion.setAerolinea(null);
        assertNull(avion.getAerolinea());
    }
    
    @Test
    public void testToString() {
        avion.setModelo("Boeing 747");
        String resultado = avion.toString();
        assertEquals("Avión Boeing 747", resultado);
    }
    
    @Test
    public void testToStringConModeloNulo() {
        avion.setModelo(null);
        String resultado = avion.toString();
        assertEquals("Avión null", resultado);
    }
    
    @Test
    public void testToStringConModeloVacio() {
        avion.setModelo("");
        String resultado = avion.toString();
        assertEquals("Avión ", resultado);
    }
    

    
  
    
    @Test
    public void testModificarTodosLosAtributos() {
        avion.setCapacidad(150);
        avion.setId("TEST001");
        avion.setModelo("Embraer E190");
        avion.setPeso(51800.0);
        avion.setAerolinea(aerolinea);
        
        assertEquals(150, avion.getCapacidad());
        assertEquals("TEST001", avion.getId());
        assertEquals("Embraer E190", avion.getModelo());
        assertEquals(51800.0, avion.getPeso(), 0.001);
        assertEquals(aerolinea, avion.getAerolinea());
    }
    
    @Test
    public void testCapacidadMaxima() {
        avion.setCapacidad(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, avion.getCapacidad());
    }
    
    @Test
    public void testPesoMaximo() {
        avion.setPeso(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, avion.getPeso(), 0.001);
    }
    
    @Test
    public void testIdConEspacios() {
        avion.setId("   AV 001   ");
        assertEquals("   AV 001   ", avion.getId());
    }
    
    @Test
    public void testModeloConEspacios() {
        avion.setModelo("   Boeing 737   ");
        assertEquals("   Boeing 737   ", avion.getModelo());
    }
    
    @Test
    public void testIdConCaracteresEspeciales() {
        avion.setId("AV-001@#$%");
        assertEquals("AV-001@#$%", avion.getId());
    }
    
    @Test
    public void testModeloConCaracteresEspeciales() {
        avion.setModelo("Boeing 737-800 MAX");
        assertEquals("Boeing 737-800 MAX", avion.getModelo());
    }
}