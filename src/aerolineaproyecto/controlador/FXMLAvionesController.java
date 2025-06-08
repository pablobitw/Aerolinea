/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.AvionDAO;
import aerolineaproyecto.modelo.dao.ClienteDAO;
import aerolineaproyecto.modelo.pojo.Avion;
import aerolineaproyecto.modelo.pojo.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Silva
 */
public class FXMLAvionesController implements Initializable {

    private TableView<?> tvEmpleados;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private ImageView btnLogo;
    @FXML
    private TableColumn<?, ?> capacidad;
    @FXML
    private TableColumn<?, ?> modelo;
    @FXML
    private TableColumn<?, ?> peso;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Avion> listaAviones;
    @FXML
    private TableView<Avion> tvAviones;

@Override
public void initialize(URL url, ResourceBundle rb) {
    configurarColumnas();
    cargarAviones();
}

private void configurarColumnas() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
    peso.setCellValueFactory(new PropertyValueFactory<>("peso"));
}

private void cargarAviones() {
    List<Avion> aviones = AvionDAO.cargarAviones();
    listaAviones = FXCollections.observableArrayList(aviones);
    tvAviones.setItems(listaAviones);
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
            Stage escenario = (Stage) tvAviones.getScene().getWindow();
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
