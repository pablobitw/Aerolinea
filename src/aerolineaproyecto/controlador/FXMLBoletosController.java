package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.VueloDAO;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLBoletosController implements Initializable {

    @FXML private TableView<Vuelo> tvVuelos;
    @FXML private TableColumn<Vuelo, Integer> id;
    @FXML private TableColumn<Vuelo, Integer> numero_pasajeros;
    @FXML private TableColumn<Vuelo, Double> costo_boleto;
    @FXML private TableColumn<Vuelo, String> tiempo_recorrido;
    @FXML private TableColumn<Vuelo, String> ciudad_salida;
    @FXML private TableColumn<Vuelo, String> ciudad_llegada;
    @FXML private TableColumn<Vuelo, String> fecha_hora_salida;
    @FXML private TableColumn<Vuelo, String> hora_llegada;
    @FXML private TableColumn<Vuelo, String> asistentes;
    @FXML private TableColumn<Vuelo, String> pilotos;
    @FXML private ImageView btnLogo;

    private ObservableList<Vuelo> listaVuelos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarVuelos();
    }

    private void configurarColumnas() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        numero_pasajeros.setCellValueFactory(new PropertyValueFactory<>("numeroPasajeros"));
        costo_boleto.setCellValueFactory(new PropertyValueFactory<>("costoBoleto"));
        tiempo_recorrido.setCellValueFactory(new PropertyValueFactory<>("tiempoRecorrido"));
        ciudad_salida.setCellValueFactory(new PropertyValueFactory<>("ciudadSalida"));
        ciudad_llegada.setCellValueFactory(new PropertyValueFactory<>("ciudadLlegada"));
        fecha_hora_salida.setCellValueFactory(new PropertyValueFactory<>("salida"));
        hora_llegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));
        asistentes.setCellValueFactory(new PropertyValueFactory<>("asistentesNombres"));
        pilotos.setCellValueFactory(new PropertyValueFactory<>("pilotosNombres"));
    }

    public void cargarVuelos() {
        List<Vuelo> vuelos = VueloDAO.cargarVuelos();
        listaVuelos = FXCollections.observableArrayList(vuelos);
        tvVuelos.setItems(listaVuelos);
    }

    public void cargarBoletos() {
        // En este controlador se manejan vuelos, no boletos,
        // así que para refrescar, se recargan los vuelos.
        cargarVuelos();
    }

    @FXML
    private void btnComprarBoleto(ActionEvent event) {
        Vuelo vueloSeleccionado = tvVuelos.getSelectionModel().getSelectedItem();

        if (vueloSeleccionado == null) {
            mostrarAlerta("Sin Selección", "Por favor, seleccione un vuelo de la tabla para comprar un boleto.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLComprarBoleto.fxml"));
            Parent root = loader.load();

            FXMLComprarBoletoController controladorComprarBoleto = loader.getController();
            controladorComprarBoleto.setVuelo(vueloSeleccionado);
            controladorComprarBoleto.setBoletosController(this); // Para poder llamar cargarBoletos desde el otro controlador

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Comprar Boleto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();

            // Después de cerrar ventana compra, refrescar vuelos
            cargarVuelos();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista de compra de boleto:");
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana para comprar el boleto. Intente de nuevo.");
        }
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void refrescarVuelos() {
        cargarVuelos();
    }

}
