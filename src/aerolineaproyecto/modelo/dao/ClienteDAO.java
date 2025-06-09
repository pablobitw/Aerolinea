package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.utilidad.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String ARCHIVO_JSON = "src/aerolineaproyecto/recursos/data/clientes.json";

    public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return clientes; // Retorna lista vacía si no existe el archivo
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Cliente>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            clientes = gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de clientes:");
            e.printStackTrace();
        }

        return clientes;
    }

    public static void guardarClientes(List<Cliente> clientes) {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs(); // Crear carpetas si no existen
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            gson.toJson(clientes, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de clientes:");
            e.printStackTrace();
        }
    }
}
