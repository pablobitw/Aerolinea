package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Avion;
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

public class AvionDAO {
    // Cambiar de 'static final' a solo 'static' para permitir modificación en tests
    private static String ARCHIVO_JSON = "data/aviones.json";
    
    // Método para permitir cambiar la ruta del archivo (solo para testing)
    protected static void setArchivoJson(String rutaArchivo) {
        ARCHIVO_JSON = rutaArchivo;
    }
    
    // Método para obtener la ruta actual del archivo (para testing)
    protected static String getArchivoJson() {
        return ARCHIVO_JSON;
    }
    
    public static List<Avion> cargarAviones() {
        List<Avion> aviones = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);
        
        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return aviones;  // Retorna lista vacía si no existe archivo
        }
        
        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Avion>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            aviones = gson.fromJson(reader, tipoLista);
            
            // Manejar caso donde gson devuelve null
            if (aviones == null) {
                aviones = new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de aviones:");
            e.printStackTrace();
        }
        
        return aviones;
    }
    
    public static void guardarAviones(List<Avion> aviones) {
        File archivo = new File(ARCHIVO_JSON);
        
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs(); // Crea carpetas si no existen
        }
        
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            gson.toJson(aviones, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de aviones:");
            e.printStackTrace();
        }
    }
}