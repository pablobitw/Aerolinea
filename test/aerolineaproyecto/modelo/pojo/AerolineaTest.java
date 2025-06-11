package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AerolineaTest {
    
    private Aerolinea aerolinea;
    private static final String ID_TEST = "AER001";
    private static final String DIRECCION_TEST = "Av. Principal 123, Ciudad";
    private static final String NOMBRE_TEST = "Aerolíneas Test";
    private static final String CONTACTO_TEST = "Juan Pérez";
    private static final String TELEFONO_TEST = "555-0123";
    
    @Before
    public void setUp() {
        aerolinea = new Aerolinea();
    }
    
    @Test
    public void testConstructorVacio() {
        assertNotNull("La aerolínea no debería ser null", aerolinea);
        assertNull("El ID debería ser null inicialmente", aerolinea.getId());
        assertNull("La dirección debería ser null inicialmente", aerolinea.getDireccion());
        assertNull("El nombre debería ser null inicialmente", aerolinea.getNombre());
        assertNull("El contacto debería ser null inicialmente", aerolinea.getContacto());
        assertNull("El teléfono debería ser null inicialmente", aerolinea.getTelefono());
    }
    
    @Test
    public void testConstructorConParametros() {
        Aerolinea aerolineaCompleta = new Aerolinea(ID_TEST, DIRECCION_TEST, 
                                                   NOMBRE_TEST, CONTACTO_TEST, TELEFONO_TEST);
        
        assertNotNull("La aerolínea no debería ser null", aerolineaCompleta);
        assertEquals("El ID no coincide", ID_TEST, aerolineaCompleta.getId());
        assertEquals("La dirección no coincide", DIRECCION_TEST, aerolineaCompleta.getDireccion());
        assertEquals("El nombre no coincide", NOMBRE_TEST, aerolineaCompleta.getNombre());
        assertEquals("El contacto no coincide", CONTACTO_TEST, aerolineaCompleta.getContacto());
        assertEquals("El teléfono no coincide", TELEFONO_TEST, aerolineaCompleta.getTelefono());
    }
    
    @Test
    public void testSetYGetId() {
        aerolinea.setId(ID_TEST);
        assertEquals("El ID no se estableció correctamente", ID_TEST, aerolinea.getId());
    }
    
    @Test
    public void testSetYGetDireccion() {
        aerolinea.setDireccion(DIRECCION_TEST);
        assertEquals("La dirección no se estableció correctamente", DIRECCION_TEST, aerolinea.getDireccion());
    }
    
    @Test
    public void testSetYGetNombre() {
        aerolinea.setNombre(NOMBRE_TEST);
        assertEquals("El nombre no se estableció correctamente", NOMBRE_TEST, aerolinea.getNombre());
    }
    
    @Test
    public void testSetYGetContacto() {
        aerolinea.setContacto(CONTACTO_TEST);
        assertEquals("El contacto no se estableció correctamente", CONTACTO_TEST, aerolinea.getContacto());
    }
    
    @Test
    public void testSetYGetTelefono() {
        aerolinea.setTelefono(TELEFONO_TEST);
        assertEquals("El teléfono no se estableció correctamente", TELEFONO_TEST, aerolinea.getTelefono());
    }
    
    @Test
    public void testSetValoresNull() {
        // Primero establecemos valores
        aerolinea.setId(ID_TEST);
        aerolinea.setDireccion(DIRECCION_TEST);
        aerolinea.setNombre(NOMBRE_TEST);
        aerolinea.setContacto(CONTACTO_TEST);
        aerolinea.setTelefono(TELEFONO_TEST);
        
        // Luego los cambiamos a null
        aerolinea.setId(null);
        aerolinea.setDireccion(null);
        aerolinea.setNombre(null);
        aerolinea.setContacto(null);
        aerolinea.setTelefono(null);
        
        // Verificamos que se puedan establecer como null
        assertNull("El ID debería poder ser null", aerolinea.getId());
        assertNull("La dirección debería poder ser null", aerolinea.getDireccion());
        assertNull("El nombre debería poder ser null", aerolinea.getNombre());
        assertNull("El contacto debería poder ser null", aerolinea.getContacto());
        assertNull("El teléfono debería poder ser null", aerolinea.getTelefono());
    }
    
    @Test
    public void testSetValoresVacios() {
        aerolinea.setId("");
        aerolinea.setDireccion("");
        aerolinea.setNombre("");
        aerolinea.setContacto("");
        aerolinea.setTelefono("");
        
        assertEquals("El ID vacío debería mantenerse", "", aerolinea.getId());
        assertEquals("La dirección vacía debería mantenerse", "", aerolinea.getDireccion());
        assertEquals("El nombre vacío debería mantenerse", "", aerolinea.getNombre());
        assertEquals("El contacto vacío debería mantenerse", "", aerolinea.getContacto());
        assertEquals("El teléfono vacío debería mantenerse", "", aerolinea.getTelefono());
    }
    
    @Test
    public void testToStringConValores() {
        aerolinea.setId(ID_TEST);
        aerolinea.setNombre(NOMBRE_TEST);
        
        String expectedString = ID_TEST + " - " + NOMBRE_TEST;
        assertEquals("El toString no genera el formato esperado", expectedString, aerolinea.toString());
    }
    
    @Test
    public void testToStringConValoresNull() {
        aerolinea.setId(null);
        aerolinea.setNombre(null);
        
        String result = aerolinea.toString();
        assertEquals("El toString con valores null debería mostrar 'null - null'", "null - null", result);
    }
    
    @Test
    public void testToStringConValoresVacios() {
        aerolinea.setId("");
        aerolinea.setNombre("");
        
        String expectedString = " - ";
        assertEquals("El toString con valores vacíos debería mostrar ' - '", expectedString, aerolinea.toString());
    }
    
    @Test
    public void testModificacionDeValores() {
        // Establecemos valores iniciales
        aerolinea.setId("INICIAL");
        aerolinea.setNombre("Nombre Inicial");
        
        // Modificamos los valores
        String nuevoId = "MODIFICADO";
        String nuevoNombre = "Nombre Modificado";
        
        aerolinea.setId(nuevoId);
        aerolinea.setNombre(nuevoNombre);
        
        assertEquals("El ID modificado no se guardó correctamente", nuevoId, aerolinea.getId());
        assertEquals("El nombre modificado no se guardó correctamente", nuevoNombre, aerolinea.getNombre());
    }
    
    @Test
    public void testValoresConEspacios() {
        String idConEspacios = "  AER 001  ";
        String nombreConEspacios = "  Aerolínea Con Espacios  ";
        
        aerolinea.setId(idConEspacios);
        aerolinea.setNombre(nombreConEspacios);
        
        assertEquals("El ID con espacios debería mantenerse tal como se estableció", 
                    idConEspacios, aerolinea.getId());
        assertEquals("El nombre con espacios debería mantenerse tal como se estableció", 
                    nombreConEspacios, aerolinea.getNombre());
    }
}