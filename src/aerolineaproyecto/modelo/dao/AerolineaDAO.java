package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Aerolinea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AerolineaDAO {

    private static final String ARCHIVO_JSON = "aerolineaproyecto/recursos/data/aerolineas.json";
    private static final String RUTA_GUARDADO = "aerolineaproyecto/recursos/data/aerolineas.json";

    public static List<Aerolinea> cargarAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        File archivoExterno = new File(RUTA_GUARDADO);
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type tipoLista = new TypeToken<List<Aerolinea>>() {}.getType();

            if (archivoExterno.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(archivoExterno), StandardCharsets.UTF_8)) {
                    aerolineas = gson.fromJson(reader, tipoLista);
                }
            } else {
                try (InputStream is = AerolineaDAO.class.getClassLoader().getResourceAsStream(ARCHIVO_JSON)) {
                    if (is != null) {
                        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                            aerolineas = gson.fromJson(reader, tipoLista);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aerolineas;
    }

    public static void guardarAerolineas(List<Aerolinea> aerolineas) {
        try {
            File carpeta = new File("aerolineaproyecto/recursos/data");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(RUTA_GUARDADO), StandardCharsets.UTF_8)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(aerolineas, writer);
            }
        } catch (Exception e) {
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
            if (aerolineas.get(i).getId() == aerolinea.getId()) {
                aerolineas.set(i, aerolinea);
                break;
            }
        }
        guardarAerolineas(aerolineas);
    }

    public static void eliminarAerolinea(int id) {
        List<Aerolinea> aerolineas = cargarAerolineas();
        aerolineas.removeIf(a -> a.getId() == id);
        guardarAerolineas(aerolineas);
    }
}
