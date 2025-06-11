package aerolineaproyecto.modelo.dao;

import aerolineaproyecto.modelo.pojo.Empleado;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class SesionManagerDAO {

    private static final String RUTA_RELATIVA_JSON = "data/empleados.json";

    public static Empleado verificarCredenciales(String user, String pass) {
        try (FileReader reader = new FileReader(RUTA_RELATIVA_JSON)) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Empleado>>() {}.getType();
            List<Empleado> empleados = gson.fromJson(reader, tipoLista);

            for (Empleado e : empleados) {
                if (e.getUser().equals(user) && e.getPass().equals(pass)) {
                    return e;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return null;
    }
}
