/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.ClienteDAO;
import aerolineaproyecto.modelo.dao.VueloDAO;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
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
public class FXMLVuelosController implements Initializable {

    @FXML
    private TableView<Vuelo> tvVuelos; // <-- Cambiado a Vuelo

    @FXML
    private TableColumn<Vuelo, Integer> id;

    @FXML
    private TableColumn<Vuelo, Integer> numero_pasajeros;

    @FXML
    private TableColumn<Vuelo, Double> costo_boleto;

    @FXML
    private TableColumn<Vuelo, String> tiempo_recorrido;

    @FXML
    private TableColumn<Vuelo, String> ciudad_salida;

    @FXML
    private TableColumn<Vuelo, String> ciudad_llegada;

    @FXML
    private TableColumn<Vuelo, String> fecha_hora_salida;

    @FXML
    private TableColumn<Vuelo, String> hora_llegada;

    @FXML
    private ImageView btnLogo;

    private ObservableList<Vuelo> listaVuelos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarVuelos();
    }

    private void configurarColumnas() {
        // En tu POJO no tienes atributo "id", elimina o ajusta esta columna si no existe
        // id.setCellValueFactory(new PropertyValueFactory<>("id"));

        numero_pasajeros.setCellValueFactory(new PropertyValueFactory<>("numero_pasajeros"));
        costo_boleto.setCellValueFactory(new PropertyValueFactory<>("costo_boleto"));
        tiempo_recorrido.setCellValueFactory(new PropertyValueFactory<>("tiempo_recorrido"));
        ciudad_salida.setCellValueFactory(new PropertyValueFactory<>("ciudad_salida"));
        ciudad_llegada.setCellValueFactory(new PropertyValueFactory<>("ciudad_llegada"));
        fecha_hora_salida.setCellValueFactory(new PropertyValueFactory<>("salida"));
        hora_llegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));
    }

    private void cargarVuelos() {
        List<Vuelo> vuelos = VueloDAO.cargarVuelos();
        listaVuelos = FXCollections.observableArrayList(vuelos);
        tvVuelos.setItems(listaVuelos);
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        // Código para agregar vuelo
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        // Código para modificar vuelo
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        // Código para eliminar vuelo
    }

    @FXML
    private void btnExportar(ActionEvent event) {
        // Código para exportar vuelos
    }

    @FXML
    private void btnLogoPresionado(ActionEvent event) {
        try {
            Stage escenario = (Stage) tvVuelos.getScene().getWindow();
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