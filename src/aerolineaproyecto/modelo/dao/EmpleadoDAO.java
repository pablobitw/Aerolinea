/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;
import aerolineaproyecto.modelo.pojo.Empleado;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PABLO
 */
public class EmpleadoDAO {

    private static final String ARCHIVO_JSON = "aerolineaproyecto/recursos/data/empleados.json";
 
    public static List<Empleado> cargarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        try (InputStream is = EmpleadoDAO.class.getClassLoader().getResourceAsStream(ARCHIVO_JSON)) {
            if (is == null) {
                System.err.println("No se encontró el archivo: " + ARCHIVO_JSON);
                return empleados;
            }

            try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                Type tipoLista = new TypeToken<List<Empleado>>() {}.getType();
                empleados = new Gson().fromJson(reader, tipoLista);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de empleados:");
            e.printStackTrace();
        }

        return empleados;
    }


    public static void guardarEmpleados(List<Empleado> empleados) {
        // Esta ruta apunta al archivo directamente editable durante desarrollo
        File archivo = new File("src/aerolineaproyecto/recursos/data/empleados.json");
        archivo.getParentFile().mkdirs(); // Por si no existen carpetas

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(empleados, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de empleados:");
            e.printStackTrace();
        }
    }

  
    public static String generarNuevoId(List<Empleado> empleados) {
        int mayor = 0;
        for (Empleado emp : empleados) {
            try {
                String soloNumero = emp.getId().replaceAll("[^0-9]", "");
                int idNum = Integer.parseInt(soloNumero);
                if (idNum > mayor) {
                    mayor = idNum;
                }
            } catch (NumberFormatException ignored) {}
        }
        return String.format("E%03d", mayor + 1);
    }
}