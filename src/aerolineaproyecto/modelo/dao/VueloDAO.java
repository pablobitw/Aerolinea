package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Vuelo;
import aerolineaproyecto.utilidad.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO {

    private static final String ARCHIVO_JSON = System.getProperty("user.home") + "/AerolineaUniAir/vuelos.json";

    public static List<Vuelo> cargarVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo externo: " + archivo.getAbsolutePath());
            return vuelos; // lista vacía si no existe archivo externo
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type tipoLista = new TypeToken<List<Vuelo>>() {}.getType();
            Gson gson = GsonUtil.createGson();
            vuelos = gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer archivo de vuelos:");
            e.printStackTrace();
        }

        return vuelos;
    }

    public static void guardarVuelos(List<Vuelo> vuelos) {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs(); // crear carpetas si no existen
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = GsonUtil.createGson();
            gson.toJson(vuelos, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar archivo de vuelos:");
            e.printStackTrace();
        }
    }

    public static boolean agregarVuelo(Vuelo vueloNuevo) {
    try {
        List<Vuelo> vuelos = cargarVuelos();
        int maxId = vuelos.stream().mapToInt(Vuelo::getId).max().orElse(0);
        vueloNuevo.setId(maxId + 1);
        vuelos.add(vueloNuevo);
        guardarVuelos(vuelos);
        return true;
    } catch (Exception e) {
        System.err.println("Error al agregar vuelo: " + e.getMessage());
        return false;
    }
}

public static boolean modificarVuelo(Vuelo vueloModificado) {
    try {
        List<Vuelo> vuelos = cargarVuelos();
        boolean encontrado = false;
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getId() == vueloModificado.getId()) {
                vuelos.set(i, vueloModificado);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            guardarVuelos(vuelos);
            return true;
        }
        return false;
    } catch (Exception e) {
        System.err.println("Error al modificar vuelo: " + e.getMessage());
        return false;
    }
}

    public static boolean eliminarVuelo(int idVuelo) {
    List<Vuelo> vuelos = cargarVuelos();
    boolean eliminado = vuelos.removeIf(v -> v.getId() == idVuelo);
    if (eliminado) {
        guardarVuelos(vuelos);
    }
    return eliminado;
}
}
