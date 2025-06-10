/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;
import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.utilidad.LocalDateAdapter; // Ajusta si usas otro adaptador o Date
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;  // Cambia si usas java.util.Date
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PABLO
 */


public class BoletoDAO {

    private static final String ARCHIVO_JSON = "src/aerolineaproyecto/recursos/data/boletos.json";

    public static List<Boleto> cargarBoletos() {
        List<Boleto> boletos = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return boletos; // Retorna lista vacía si no existe el archivo
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Boleto>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Cambia según tipo fecha
                    .create();

            boletos = gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de boletos:");
            e.printStackTrace();
        }

        return boletos;
    }

    public static void guardarBoletos(List<Boleto> boletos) {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs(); // Crear carpetas si no existen
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Cambia según tipo fecha
                    .create();

            gson.toJson(boletos, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de boletos:");
            e.printStackTrace();
        }
    }
}