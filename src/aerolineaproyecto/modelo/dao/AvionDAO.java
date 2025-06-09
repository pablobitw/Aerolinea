/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.utilidad.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PABLO
 */
public class AvionDAO {
    private static final String ARCHIVO_JSON = "aerolineaproyecto/recursos/data/aviones.json";

    public static List<Avion> cargarAviones() {
        List<Avion> aviones = new ArrayList<>();

        try (InputStream is = AvionDAO.class.getClassLoader().getResourceAsStream(ARCHIVO_JSON)) {
            if (is == null) {
                System.err.println("No se encontró el archivo: " + ARCHIVO_JSON);
                return aviones;
            }

            try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                Type tipoLista = new TypeToken<List<Avion>>() {}.getType();

                Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

                aviones = gson.fromJson(reader, tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aviones;
    }

    public static void guardarAviones(List<Avion> aviones) {
        try {
            File file = new File(AvionDAO.class.getClassLoader().getResource(ARCHIVO_JSON).toURI());

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

                gson.toJson(aviones, writer);
            }

        } catch (URISyntaxException | NullPointerException e) {
            System.err.println("No se pudo acceder al archivo de escritura: " + ARCHIVO_JSON);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}