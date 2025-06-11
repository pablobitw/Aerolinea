package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Aerolinea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AerolineaDAO {
    private static final String ARCHIVO_JSON = "data/aerolineas.json";
    
    public static List<Aerolinea> cargarAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return aerolineas;  // Devuelve lista vacía si no existe archivo
        }
        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Aerolinea>>() {}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            aerolineas = gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de aerolíneas:");
            e.printStackTrace();
        }
        return aerolineas;
    }
    
    public static void guardarAerolineas(List<Aerolinea> aerolineas) {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(aerolineas, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de aerolíneas:");
            e.printStackTrace();
        }
    }
    
    public static void agregarAerolinea(Aerolinea aerolinea) {
        List<Aerolinea> aerolineas = cargarAerolineas();
        aerolineas.add(aerolinea);
        guardarAerolineas(aerolineas);
    }
    
    public static void actualizarAerolinea(Aerolinea aerolinea) {
        List<Aerolinea> aerolineas = cargarAerolineas();
        for (int i = 0; i < aerolineas.size(); i++) {
            // CORRECCIÓN: Usar .equals() para comparar Strings
            if (aerolineas.get(i).getId().equals(aerolinea.getId())) {
                aerolineas.set(i, aerolinea);
                break;
            }
        }
        guardarAerolineas(aerolineas);
    }
    
    public static void eliminarAerolinea(String id) {
        List<Aerolinea> aerolineas = cargarAerolineas();
        aerolineas.removeIf(a -> a.getId().equals(id));
        guardarAerolineas(aerolineas);
    }
}