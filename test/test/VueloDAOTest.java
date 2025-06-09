package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VueloDAOTest {
    
    public VueloDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Iniciando tests de VueloDAO...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finalizando tests de VueloDAO.");
    }
    
    @Before
    public void setUp() {
        System.out.println("Configurando test individual...");
    }
    
    @After
    public void tearDown() {
        System.out.println("Limpiando después del test...");
    }

    @Test
    public void testDummy() {
        assertTrue("Esta prueba siempre debe pasar", true);
    }
}
