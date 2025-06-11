package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Vuelo;
import org.junit.*;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VueloDAOTest {

    private static final String RUTA_ORIGINAL = getRutaJson();
    private static final String RUTA_PRUEBA = "data/vuelos_test.json";
    private List<Vuelo> vuelosPrueba;

    @Before
    public void setUp() throws Exception {
        vuelosPrueba = new ArrayList<>();
  

        setRutaJson(RUTA_PRUEBA);
        VueloDAO.guardarVuelos(vuelosPrueba);
    }

    @Test
    public void testGuardarYCargarVuelos() {
        List<Vuelo> cargados = VueloDAO.cargarVuelos();
        assertNotNull(cargados);
        assertEquals(2, cargados.size());
   
    }

    @Test
    public void testArchivoInexistente() {
        File archivo = new File(RUTA_PRUEBA);
        if (archivo.exists()) archivo.delete();
        List<Vuelo> vacio = VueloDAO.cargarVuelos();
        assertNotNull(vacio);
        assertTrue(vacio.isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        File archivo = new File(RUTA_PRUEBA);
        if (archivo.exists()) archivo.delete();
        setRutaJson(RUTA_ORIGINAL);
    }

    private static void setRutaJson(String nuevaRuta) throws Exception {
        Field field = VueloDAO.class.getDeclaredField("ARCHIVO_JSON");
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
        field.set(null, nuevaRuta);
    }

    private static String getRutaJson() {
        try {
            Field field = VueloDAO.class.getDeclaredField("ARCHIVO_JSON");
            field.setAccessible(true);
            return (String) field.get(null);
        } catch (Exception e) {
            return "data/vuelos.json";
        }
    }
}
