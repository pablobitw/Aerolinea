/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Silva
 */
public class FXMLAerolineasController implements Initializable {

    @FXML
    private TableView<?> tvAerolineas;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> nombre;
    @FXML
    private TableColumn<?, ?> tipoEmpleado;
    @FXML
    private TableColumn<?, ?> direccion;
    @FXML
    private TableColumn<?, ?> fechaNacimiento;
    @FXML
    private TableColumn<?, ?> salario;
    @FXML
    private ImageView btnLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregar(ActionEvent event) {
    }

    @FXML
    private void btnModificar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    @FXML
    private void btnExportar(ActionEvent event) {
    }

    @FXML
    private void btnLogoPresionado(ActionEvent event) {
        try {
            Stage escenario = (Stage) tvAerolineas.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLPantallaPrincipal.fxml"));
            Parent root = loader.load();

            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Pantalla Principal");
            escenario.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
