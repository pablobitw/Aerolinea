/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.utilidad;

/**
 *
 * @author Pablo Silva
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class Utilidad {
    public static void mostrarAlertaSimple(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
        
    }
    public static Stage obtenerEscenarioComponente(Control componente){
        return (Stage) componente.getScene().getWindow();
    }
}
