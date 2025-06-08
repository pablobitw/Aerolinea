package aerolineaproyecto;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import aerolineaproyecto.utilidad.Utilidad;
/**
 * @author Pablo Silva
 */

public class AerolineaProyecto extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("/aerolineaproyecto/vista/FXMLInicioSesion.fxml"));
            Scene escenaInicioSesion = new Scene(vista);
            primaryStage.setScene(escenaInicioSesion);
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al iniciar", "No se pudo cargar la ventana de inicio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

