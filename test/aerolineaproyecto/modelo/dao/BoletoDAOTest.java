package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Boleto;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class BoletoDAOTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private String archivoOriginal;
    private File archivoTemporal;
    private Boleto boletoTest1;
    private Boleto boletoTest2;
    private Boleto boletoTest3;
    
    @Before
    public void setUp() throws IOException {
        // Crear archivo temporal para las pruebas
        archivoTemporal = tempFolder.newFile("boletos_test.json");
        
        // Modificar temporalmente la ruta del archivo (usando reflection o system property)
        archivoOriginal = System.getProperty("user.home");
        System.setProperty("user.home", tempFolder.getRoot().getAbsolutePath() + "/AerolineaUniAir");
        
        // Crear directorio de prueba
        new File(tempFolder.getRoot().getAbsolutePath() + "/AerolineaUniAir").mkdirs();
        
        // Crear boletos de prueba
        boletoTest1 = crearBoletoTest("BOL001", 1, "A1", "Juan Pérez", "12345678", 
                                      LocalDate.of(2024, 6, 15), 150.00);
        boletoTest2 = crearBoletoTest("BOL002", 1, "A2", "María García", "87654321", 
                                      LocalDate.of(2024, 6, 15), 150.00);
        boletoTest3 = crearBoletoTest("BOL003", 2, "B1", "Carlos López", "11223344", 
                                      LocalDate.of(2024, 6, 16), 200.00);
    }
    
    @After
    public void tearDown() {
        // Restaurar configuración original
        System.setProperty("user.home", archivoOriginal);
    }
    
    private Boleto crearBoletoTest(String id, int idVuelo, String numAsiento, 
                                  String nombrePasajero, String identificacion,
                                  LocalDate fechaEmision, double precio) {
        // Asumiendo que Boleto tiene un constructor con estos parámetros
        // Si no, ajusta según tu implementación real
        Boleto boleto = new Boleto();
        boleto.setId(id);
        boleto.setIdVuelo(idVuelo);
        boleto.setNumAsiento(numAsiento);
        return boleto;
    }
    
    @Test
    public void testCargarBoletosArchivoNoExiste() {
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertNotNull("La lista de boletos no debería ser null", boletos);
        assertTrue("La lista debería estar vacía cuando no existe el archivo", boletos.isEmpty());
    }
    
    @Test
    public void testGuardarBoletosListaVacia() throws IOException {
        List<Boleto> boletosVacios = new ArrayList<>();
        boolean resultado = BoletoDAO.guardarBoletos(boletosVacios);
        assertTrue("Debería poder guardar una lista vacía", resultado);
        
        // Verificar que se creó el archivo
        File archivo = new File(System.getProperty("user.home") + "/boletos.json");
        assertTrue("El archivo debería existir después de guardar", archivo.exists());
    }
    
    @Test
    public void testGuardarYCargarBoletos() throws IOException {
        List<Boleto> boletosOriginales = new ArrayList<>();
        boletosOriginales.add(boletoTest1);
        boletosOriginales.add(boletoTest2);
        
        // Guardar boletos
        boolean guardado = BoletoDAO.guardarBoletos(boletosOriginales);
        assertTrue("Debería guardar los boletos correctamente", guardado);
        
        // Cargar boletos
        List<Boleto> boletosCargados = BoletoDAO.cargarBoletos();
        assertNotNull("Los boletos cargados no deberían ser null", boletosCargados);
        assertEquals("Debería cargar el mismo número de boletos", 2, boletosCargados.size());
        
        // Verificar datos del primer boleto
        Boleto primerBoleto = boletosCargados.get(0);
        assertEquals("El ID del primer boleto debería coincidir", "BOL001", primerBoleto.getId());
        assertEquals("El vuelo del primer boleto debería coincidir", 1, primerBoleto.getIdVuelo());
        assertEquals("El asiento del primer boleto debería coincidir", "A1", primerBoleto.getNumAsiento());
    }
    
    @Test
    public void testSaveBoletoValido() {
        boolean resultado = BoletoDAO.save(boletoTest1);
        assertTrue("Debería guardar el boleto válido", resultado);
        
        // Verificar que se guardó
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertEquals("Debería haber un boleto guardado", 1, boletos.size());
        assertEquals("El ID debería coincidir", "BOL001", boletos.get(0).getId());
    }
    
    @Test
    public void testSaveBoletoNull() {
        boolean resultado = BoletoDAO.save(null);
        assertFalse("No debería guardar un boleto null", resultado);
    }
    
    @Test
    public void testSaveBoletoConIdNull() {
        Boleto boletoSinId = crearBoletoTest(null, 1, "A1", "Test", "123", 
                                           LocalDate.now(), 100.0);
        boolean resultado = BoletoDAO.save(boletoSinId);
        assertFalse("No debería guardar un boleto con ID null", resultado);
    }
    
    @Test
    public void testSaveBoletoIdDuplicado() {
        // Guardar primer boleto
        BoletoDAO.save(boletoTest1);
        
        // Intentar guardar otro boleto con el mismo ID
        Boleto boletoDuplicado = crearBoletoTest("BOL001", 2, "B1", "Otro Pasajero", 
                                                "99999999", LocalDate.now(), 200.0);
        boolean resultado = BoletoDAO.save(boletoDuplicado);
        assertFalse("No debería guardar un boleto con ID duplicado", resultado);
        
        // Verificar que solo hay un boleto
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertEquals("Solo debería haber un boleto", 1, boletos.size());
    }
    
    @Test
    public void testSaveAsientoOcupado() {
        // Guardar primer boleto
        BoletoDAO.save(boletoTest1);
        
        // Intentar guardar otro boleto en el mismo vuelo y asiento
        Boleto boletoMismoAsiento = crearBoletoTest("BOL004", 1, "A1", "Otro Pasajero", 
                                                   "99999999", LocalDate.now(), 150.0);
        boolean resultado = BoletoDAO.save(boletoMismoAsiento);
        assertFalse("No debería guardar un boleto en asiento ocupado", resultado);
        
        // Verificar que solo hay un boleto
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertEquals("Solo debería haber un boleto", 1, boletos.size());
    }
    
    @Test
    public void testSaveAsientoEnDiferenteVuelo() {
        // Guardar primer boleto
        BoletoDAO.save(boletoTest1); // Vuelo 1, Asiento A1
        
        // Guardar boleto en mismo asiento pero diferente vuelo
        Boleto boletoOtroVuelo = crearBoletoTest("BOL004", 2, "A1", "Otro Pasajero", 
                                                "99999999", LocalDate.now(), 200.0);
        boolean resultado = BoletoDAO.save(boletoOtroVuelo);
        assertTrue("Debería poder guardar el mismo asiento en diferente vuelo", resultado);
        
        // Verificar que hay dos boletos
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertEquals("Debería haber dos boletos", 2, boletos.size());
    }
    
    @Test
    public void testObtenerBoletosPorVueloSinBoletos() {
        List<Boleto> boletos = BoletoDAO.obtenerBoletosPorVuelo(999);
        assertNotNull("La lista no debería ser null", boletos);
        assertTrue("La lista debería estar vacía para vuelo inexistente", boletos.isEmpty());
    }
    
    @Test
    public void testObtenerBoletosPorVueloConBoletos() {
        // Guardar boletos de prueba
        BoletoDAO.save(boletoTest1); // Vuelo 1
        BoletoDAO.save(boletoTest2); // Vuelo 1
        BoletoDAO.save(boletoTest3); // Vuelo 2
        
        // Obtener boletos del vuelo 1
        List<Boleto> boletosVuelo1 = BoletoDAO.obtenerBoletosPorVuelo(1);
        assertEquals("Debería haber 2 boletos para el vuelo 1", 2, boletosVuelo1.size());
        
        // Obtener boletos del vuelo 2
        List<Boleto> boletosVuelo2 = BoletoDAO.obtenerBoletosPorVuelo(2);
        assertEquals("Debería haber 1 boleto para el vuelo 2", 1, boletosVuelo2.size());
        
        // Verificar que los boletos corresponden al vuelo correcto
        for (Boleto boleto : boletosVuelo1) {
            assertEquals("Todos los boletos deberían ser del vuelo 1", 1, boleto.getIdVuelo());
        }
        
        for (Boleto boleto : boletosVuelo2) {
            assertEquals("Todos los boletos deberían ser del vuelo 2", 2, boleto.getIdVuelo());
        }
    }
    
    @Test
    public void testFindByVuelo() {
        // Guardar boletos de prueba
        BoletoDAO.save(boletoTest1); // Vuelo 1
        BoletoDAO.save(boletoTest3); // Vuelo 2
        
        // Usar findByVuelo
        List<Boleto> boletos = BoletoDAO.findByVuelo(1);
        assertEquals("Debería encontrar 1 boleto para el vuelo 1", 1, boletos.size());
        assertEquals("El boleto debería ser del vuelo 1", 1, boletos.get(0).getIdVuelo());
    }
    
    @Test
    public void testFindByVueloListaVacia() {
        List<Boleto> boletos = BoletoDAO.findByVuelo(999);
        assertNotNull("La lista no debería ser null", boletos);
        assertTrue("La lista debería estar vacía para vuelo inexistente", boletos.isEmpty());
    }
    
    @Test
    public void testGuardarMultiplesBoletos() {
        // Guardar varios boletos
        assertTrue("Debería guardar el primer boleto", BoletoDAO.save(boletoTest1));
        assertTrue("Debería guardar el segundo boleto", BoletoDAO.save(boletoTest2));
        assertTrue("Debería guardar el tercer boleto", BoletoDAO.save(boletoTest3));
        
        // Verificar que todos se guardaron
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertEquals("Deberían haberse guardado 3 boletos", 3, boletos.size());
    }
    
    @Test
    public void testManejoBoletoNullEnLista() throws IOException {
        // Crear archivo JSON con datos inválidos manualmente para probar robustez
        String jsonConNull = "[]"; // JSON válido pero vacío
        
        File archivo = new File(System.getProperty("user.home") + "/boletos.json");
        archivo.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(jsonConNull);
        }
        
        List<Boleto> boletos = BoletoDAO.cargarBoletos();
        assertNotNull("La lista no debería ser null", boletos);
        assertEquals("La lista debería estar vacía", 0, boletos.size());
    }
}