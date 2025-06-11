package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Cliente;
import org.junit.*;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClienteDAOTest {

    private static final String ARCHIVO_ORIGINAL = getRutaArchivoClienteDAO();
    private static final String ARCHIVO_TEMPORAL = "data/clientes_test.json";
    private List<Cliente> clientesPrueba;

    @Before
    public void setUp() throws Exception {
        clientesPrueba = new ArrayList<>();
        clientesPrueba.add(new Cliente("Luis", "Martínez", "luism@gmail.com", LocalDate.of(1995, 3, 14)));
        clientesPrueba.add(new Cliente("María", "Ramírez", "maria.ramirez@yahoo.com", LocalDate.of(1980, 7, 2)));

        setRutaArchivoClienteDAO(ARCHIVO_TEMPORAL);
    }

    @Test
    public void testGuardarYCargarClientes() {
        ClienteDAO.guardarClientes(clientesPrueba);
        List<Cliente> cargados = ClienteDAO.cargarClientes();
        assertNotNull(cargados);
        assertEquals(clientesPrueba.size(), cargados.size());
    }

    @Test
    public void testCargarClientesArchivoInexistente() {
        File archivo = new File(ARCHIVO_TEMPORAL);
        if (archivo.exists()) archivo.delete();
        List<Cliente> clientes = ClienteDAO.cargarClientes();
        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        File archivo = new File(ARCHIVO_TEMPORAL);
        if (archivo.exists()) archivo.delete();
        setRutaArchivoClienteDAO(ARCHIVO_ORIGINAL);
    }

    private static void setRutaArchivoClienteDAO(String nuevaRuta) throws Exception {
        Field field = ClienteDAO.class.getDeclaredField("ARCHIVO_JSON");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
        field.set(null, nuevaRuta);
    }

    private static String getRutaArchivoClienteDAO() {
        try {
            Field field = ClienteDAO.class.getDeclaredField("ARCHIVO_JSON");
            field.setAccessible(true);
            return (String) field.get(null);
        } catch (Exception e) {
            return "data/clientes.json";
        }
    }
}
