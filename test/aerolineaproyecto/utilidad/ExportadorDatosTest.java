import aerolineaproyecto.modelo.pojo.Empleado;
import aerolineaproyecto.utilidad.ExportadorDatos;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExportadorDatosTest {

     @Test
    public void testExportarCSVEmpleados() throws IOException {
        // Preparar datos de prueba con nuevo constructor
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("1", "Juan Perez", "juanp", "1234", "M",
                "Administrador", "Calle Falsa 123", "1980-01-01", 1200.50));
        empleados.add(new Empleado("2", "Maria Lopez", "marial", "abcd", "F",
                "Vendedor", "Avenida Siempre Viva 742", "1990-05-15", 950.00));

        Path tempFile = Files.createTempFile("empleados_test", ".csv");
        ExportadorDatos.exportarCSV(empleados, tempFile.toString());
        List<String> lineas = Files.readAllLines(tempFile);

        assertEquals("ID,Nombre,Tipo,Dirección,Fecha Nacimiento,Salario", lineas.get(0));
        assertTrue(lineas.get(1).startsWith("1,Juan Perez,Administrador"));

        assertTrue(lineas.get(2).startsWith("2,Maria Lopez,Vendedor"));

     
        Files.deleteIfExists(tempFile);
    }
}