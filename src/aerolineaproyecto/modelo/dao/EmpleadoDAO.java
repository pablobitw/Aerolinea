package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Empleado;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private static final String RUTA_RELATIVA_JSON = "data/empleados.json";

    public static List<Empleado> cargarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        File archivo = new File(RUTA_RELATIVA_JSON);

        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return empleados;
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Empleado>>() {}.getType();
            empleados = new Gson().fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de empleados:");
            e.printStackTrace();
        }

        return empleados;
    }

    public static void guardarEmpleados(List<Empleado> empleados) {
        File archivo = new File(RUTA_RELATIVA_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(empleados, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
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
