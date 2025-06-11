/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package aerolineaproyecto.utilidad;

import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PABLO
 */
public class GeneradorBoletoTest {
    
    public GeneradorBoletoTest() {
    }
    
     @Test
    public void testGenerarPDF_CreaArchivoCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setNombres("Juan Pérez");

        Vuelo vuelo = new Vuelo();
        vuelo.setId(123);
        vuelo.setCiudadSalida("Lima");
        vuelo.setCiudadLlegada("Cusco");
        vuelo.setCostoBoleto(150.0);

        Boleto boleto = new Boleto();
        boleto.setNumAsiento("12A");

        String rutaSalida = "test_boleto.pdf";

        try {
            GenerarBoleto.generarPDF(cliente, vuelo, boleto, rutaSalida);

            File archivo = new File(rutaSalida);
            assertTrue("El archivo PDF debe existir", archivo.exists());
            assertTrue("El archivo PDF no debe estar vacío", archivo.length() > 0);

            archivo.delete();

        } catch (Exception e) {
            fail("No se esperaba excepción: " + e.getMessage());
        }
    }
}