/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Empleado;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PABLO
 */
public class ClienteDAO {

    private static final String ARCHIVO_JSON = "aerolineaproyecto/recursos/data/clientes.json";

    public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (InputStream is = ClienteDAO.class.getClassLoader().getResourceAsStream(ARCHIVO_JSON)) {
            if (is == null) {
                System.err.println("No se encontró el archivo: " + ARCHIVO_JSON);
                return clientes;
            }

            try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                Type tipoLista = new TypeToken<List<Cliente>>() {}.getType();

                Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

                clientes = gson.fromJson(reader, tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    public static void guardarClientes(List<Cliente> clientes) {
    try {
        File file = new File(ClienteDAO.class.getClassLoader().getResource(ARCHIVO_JSON).toURI());

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

            gson.toJson(clientes, writer);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}