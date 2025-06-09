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

    private static final String RECURSO_JSON = "aerolineaproyecto/recursos/data/vuelos.json";
    // Ruta para almacenar datos fuera del JAR (editable)
    private static final String ARCHIVO_DATOS = System.getProperty("user.home") + "/AerolineaUniAir/vuelos.json";

    public static List<Vuelo> cargarVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        File archivoExterno = new File(ARCHIVO_DATOS);

        try (Reader reader = archivoExterno.exists()
                ? new InputStreamReader(new FileInputStream(archivoExterno), StandardCharsets.UTF_8)
                : new InputStreamReader(VueloDAO.class.getClassLoader().getResourceAsStream(RECURSO_JSON), StandardCharsets.UTF_8)) {

            if (reader == null) {
                System.err.println("No se encontró el archivo: " + RECURSO_JSON);
                return vuelos;
            }

            Type tipoLista = new TypeToken<List<Vuelo>>() {}.getType();
            Gson gson = GsonUtil.createGson();
            vuelos = gson.fromJson(reader, tipoLista);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vuelos;
    }

    public static boolean guardarVuelos(List<Vuelo> vuelos) {
        try {
            File archivoExterno = new File(ARCHIVO_DATOS);
            archivoExterno.getParentFile().mkdirs(); // crear directorios si no existen

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivoExterno), StandardCharsets.UTF_8)) {
                Gson gson = GsonUtil.createGson();
                gson.toJson(vuelos, writer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean agregarVuelo(Vuelo vueloNuevo) {
        List<Vuelo> vuelos = cargarVuelos();
        int maxId = vuelos.stream().mapToInt(Vuelo::getId).max().orElse(0);
        vueloNuevo.setId(maxId + 1);
        vuelos.add(vueloNuevo);
        return guardarVuelos(vuelos);
    }

    public static boolean modificarVuelo(Vuelo vueloModificado) {
        List<Vuelo> vuelos = cargarVuelos();
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getId() == vueloModificado.getId()) {
                vuelos.set(i, vueloModificado);
                return guardarVuelos(vuelos);
            }
        }
        return false;
    }

    public static boolean eliminarVuelo(Vuelo vueloAEliminar) {
        List<Vuelo> vuelos = cargarVuelos();
        boolean eliminado = vuelos.removeIf(v -> v.getId() == vueloAEliminar.getId());
        if (eliminado) {
            return guardarVuelos(vuelos);
        }
        return false;
    }
}
