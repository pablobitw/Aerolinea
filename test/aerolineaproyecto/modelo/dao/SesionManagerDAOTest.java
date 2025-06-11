package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Empleado;
import org.junit.*;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SesionManagerDAOTest {

    private static final String RUTA_ORIGINAL = getRutaJson();
    private static final String RUTA_PRUEBA = "data/empleados_test.json";

    @Before
    public void setUp() throws Exception {
        String json = "[\n" +
                "  {\"nombre\":\"Carlos\",\"apellido\":\"Lopez\",\"user\":\"admin\",\"pass\":\"1234\"},\n" +
                "  {\"nombre\":\"Laura\",\"apellido\":\"Gomez\",\"user\":\"laura\",\"pass\":\"abcd\"}\n" +
                "]";
        File archivo = new File(RUTA_PRUEBA);
        archivo.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(json);
        }
        setRutaJson(RUTA_PRUEBA);
    }

    @Test
    public void testCredencialesCorrectas() {
        Empleado empleado = SesionManagerDAO.verificarCredenciales("admin", "1234");
        assertNotNull(empleado);
        assertEquals("Carlos", empleado.getNombre());
    }

    @Test
    public void testCredencialesIncorrectas() {
        Empleado empleado = SesionManagerDAO.verificarCredenciales("admin", "wrong");
        assertNull(empleado);
    }

    @Test
    public void testUsuarioInexistente() {
        Empleado empleado = SesionManagerDAO.verificarCredenciales("noexiste", "1234");
        assertNull(empleado);
    }

    @After
    public void tearDown() throws Exception {
        File archivo = new File(RUTA_PRUEBA);
        if (archivo.exists()) archivo.delete();
        setRutaJson(RUTA_ORIGINAL);
    }

    private static void setRutaJson(String nuevaRuta) throws Exception {
        Field field = SesionManagerDAO.class.getDeclaredField("RUTA_RELATIVA_JSON");
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
        field.set(null, nuevaRuta);
    }

    private static String getRutaJson() {
        try {
            Field field = SesionManagerDAO.class.getDeclaredField("RUTA_RELATIVA_JSON");
            field.setAccessible(true);
            return (String) field.get(null);
        } catch (Exception e) {
            return "data/empleados.json";
        }
    }
}
