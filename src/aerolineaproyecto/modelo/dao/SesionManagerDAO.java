/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.modelo.dao;
import aerolineaproyecto.modelo.pojo.Empleado;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author PABLO
 */
public class SesionManagerDAO {

    private static final String ARCHIVO_EMPLEADOS = "aerolineaproyecto/recursos/data/empleados.json";

    public static Empleado verificarCredenciales(String user, String pass) {
        try (InputStream inputStream = SesionManagerDAO.class.getClassLoader().getResourceAsStream(ARCHIVO_EMPLEADOS)) {
          if (inputStream == null) {
                System.out.println("No se encontró el archivo " + ARCHIVO_EMPLEADOS);
                return null;
            }
            InputStreamReader reader = new InputStreamReader(inputStream);
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
