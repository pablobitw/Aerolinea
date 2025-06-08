/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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
            aviones = new Gson().fromJson(reader, tipoLista);
        }

    } catch (IOException e) {
        System.err.println("Error al leer el archivo de clientes:");
        e.printStackTrace();
    }

    return aviones;
}
    
}
    
