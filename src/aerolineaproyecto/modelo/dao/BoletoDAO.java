package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Boleto;
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
import java.util.Objects;

public class BoletoDAO {

    private static final String ARCHIVO_JSON = System.getProperty("user.home") + "/AerolineaUniAir/boletos.json";

    public static List<Boleto> cargarBoletos() {
        List<Boleto> boletos = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo externo: " + archivo.getAbsolutePath());
            return boletos; // Retorna lista vacía si no existe archivo externo
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<Boleto>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            boletos = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error al leer archivo de boletos:");
            e.printStackTrace();
        }

        return boletos;
    }

    public static boolean guardarBoletos(List<Boleto> boletos) {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs(); // Crear carpetas si no existen
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            gson.toJson(boletos, writer);
            System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar archivo de boletos:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean save(Boleto nuevoBoleto) {
        if (nuevoBoleto == null || nuevoBoleto.getId() == null) {
            System.err.println("Error: El boleto o su ID es null");
            return false;
        }

        List<Boleto> boletos = cargarBoletos();

        for (Boleto b : boletos) {
            if (b == null) continue;

            if (Objects.equals(b.getId(), nuevoBoleto.getId())) {
                System.err.println("Error: Ya existe un boleto con ese ID.");
                return false;
            }
            if (Objects.equals(b.getIdVuelo(), nuevoBoleto.getIdVuelo()) &&
                Objects.equals(b.getNumAsiento(), nuevoBoleto.getNumAsiento())) {
                System.err.println("Error: Asiento ya ocupado en este vuelo.");
                return false;
            }
        }

        boletos.add(nuevoBoleto);
        return guardarBoletos(boletos);
    }

    public static List<Boleto> obtenerBoletosPorVuelo(int idVuelo) {
        List<Boleto> boletos = cargarBoletos();
        List<Boleto> resultado = new ArrayList<>();
        for (Boleto boleto : boletos) {
            if (boleto != null && boleto.getIdVuelo() == idVuelo) {
                resultado.add(boleto);
            }
        }
        return resultado;
    }

    public static List<Boleto> findByVuelo(int idVuelo) {
        return obtenerBoletosPorVuelo(idVuelo);
    }
}
